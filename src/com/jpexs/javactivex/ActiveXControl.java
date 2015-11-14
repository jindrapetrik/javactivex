package com.jpexs.javactivex;

import com.jpexs.javactivex.jna.Kernel32;
import com.jpexs.javactivex.jna.SHELLEXECUTEINFO;
import com.jpexs.javactivex.jna.Shell32;
import com.jpexs.javactivex.jna.WinNT;
import com.jpexs.javactivex.jna.WinUser;
import com.sun.jna.Native;
import com.sun.jna.Platform;
import com.sun.jna.WString;
import com.sun.jna.ptr.IntByReference;
import java.awt.Event;
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.WeakHashMap;

/**
 * ActiveX control Component
 *
 * @author JPEXS
 */
public class ActiveXControl {

    private static WinNT.HANDLE pipe;
    private static WinNT.HANDLE process;
    private static final int CMD_ECHO = 0;
    private static final int CMD_NEW = 1;
    private static final int CMD_OBJ_DESTROY = 2;
    private static final int CMD_DESTROYALL = 3;
    private static final int CMD_TYPE_LIST_PROPERTIES = 4;
    private static final int CMD_TYPE_LIST_METHODS = 5;
    private static final int CMD_TYPE_LIST_EVENTS = 6;
    private static final int CMD_OBJ_RESIZE = 7;
    private static final int CMD_OBJ_GET_PROPERTY = 8;
    private static final int CMD_OBJ_SET_PROPERTY = 9;
    private static final int CMD_OBJ_SET_PARENT = 10;
    private static final int CMD_OBJ_CALL_METHOD = 11;
    private static final int CMD_TYPE_GET_METHOD_PARAMS = 12;
    private static final int CMD_GET_OCX_CLASSES = 13;
    private static final int CMD_GET_REGISTERED_CLASSES = 14;
    private static final int CMD_TYPE_GET_PROPERTY_TYPE = 15;
    private static final int CMD_TYPE_GET_INFO = 16;

    private static final Object AXLOCK = new Object();

    private int hwnd;
    private String guid;
    private String docString;
    private long cid = -1;
    private String progId;
    private boolean attached = false;
    private String className;

    private static final int ECHO_INTERVAL = 100;

    private final static Map<String, String> boxedMap = new HashMap<>();
    private static Timer syncTimer = new Timer();

    private Panel panel;

    private boolean disposed = false;

    private static Map<Long, ActiveXControl> instances = new WeakHashMap<Long, ActiveXControl>();

    static {

        if (!Platform.isWindows()) {
            throw new UnsupportedOperationException("Active X is available on Windows only.");
        }

        boxedMap.put("boolean", "Boolean");
        boxedMap.put("byte", "Byte");
        boxedMap.put("short", "Short");
        boxedMap.put("char", "Character");
        boxedMap.put("int", "Integer");
        boxedMap.put("long", "Long");
        boxedMap.put("float", "Float");
        boxedMap.put("double", "Double");

        String path = "";
        try {
            path = URLDecoder.decode(ActiveXControl.class.getProtectionDomain().getCodeSource().getLocation().getPath(), "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            throw new Error(ex);
        }
        String appDir = new File(path).getParentFile().getAbsolutePath();
        if (!appDir.endsWith("\\")) {
            appDir += "\\";
        }

        InputStream exeStream = ActiveXControl.class.getClassLoader().getResourceAsStream("com/jpexs/javactivex/server/ActiveXServer.exe");
        if (exeStream == null) {
            throw new ActiveXException("Cannot load javactivex server stream");
        }

        File exeFile = new File(System.getProperty("java.io.tmpdir") + File.separator + "javactivex_" + System.currentTimeMillis() + ".exe");
        final int BUFSIZE = 1024;
        byte buf[] = new byte[BUFSIZE];
        try (FileOutputStream fos = new FileOutputStream(exeFile)) {
            int cnt;
            while ((cnt = exeStream.read(buf)) > 0) {
                fos.write(buf, 0, cnt);
            }
        } catch (IOException ex) {
            throw new ActiveXException("Cannot load JavactiveX server");
        }

        exeFile.deleteOnExit();
        String exePath = exeFile.getAbsolutePath();

        String instName = "" + System.currentTimeMillis();

        String pipeName = "\\\\.\\pipe\\activex_server_" + instName;
        pipe = Kernel32.INSTANCE.CreateNamedPipe(pipeName, Kernel32.PIPE_ACCESS_DUPLEX, Kernel32.PIPE_TYPE_BYTE, 1, 4096, 4096, 0, null);

        SHELLEXECUTEINFO sei = new SHELLEXECUTEINFO();
        sei.fMask = 0x00000040;
        sei.lpFile = new WString(exePath);
        sei.lpParameters = new WString(instName);
        sei.nShow = WinUser.SW_NORMAL;
        Shell32.INSTANCE.ShellExecuteEx(sei);
        process = sei.hProcess;

        Kernel32.INSTANCE.ConnectNamedPipe(pipe, null);

        echo();

        syncTimer.schedule(new TimerTask() {

            @Override
            public void run() {

                try {
                    echo();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
                /*
                 if (syncTimer != null) {
                 syncTimer.cancel();
                 syncTimer = null;
                 }
                 */
            }
        }, ECHO_INTERVAL, ECHO_INTERVAL);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                unload();
            }

        });
    }

    private static boolean unloaded = false;

    public static void unload() {
        if (unloaded) {
            return;
        }
        writeCommand(CMD_DESTROYALL);
        Kernel32.INSTANCE.CloseHandle(pipe);
        Kernel32.INSTANCE.TerminateProcess(process, 0);
        unloaded = true;
    }

    private static Map<Long, Map<String, List<ActiveXEventListener>>> listeners = new HashMap<>();

    public String getClassName() {
        return className;
    }

    private static void addControlEventListener(String eventName, ActiveXControl control, ActiveXEventListener listener) {
        if (!listeners.containsKey(control.cid)) {
            listeners.put(control.cid, new HashMap<String, List<ActiveXEventListener>>());
        }

        if (eventName == null) {
            eventName = "*";
        }
        if (!listeners.get(control.cid).containsKey(eventName)) {
            listeners.get(control.cid).put(eventName, new ArrayList<ActiveXEventListener>());
        }
        listeners.get(control.cid).get(eventName).add(listener);
    }

    public long getCid() {
        return cid;
    }

    private static void removeControlEventListener(String eventName, ActiveXControl control, ActiveXEventListener listener) {
        if (!listeners.containsKey(control.cid)) {
            return;
        }
        if (eventName == null) {
            eventName = "*";
        }
        if (!listeners.get(control.cid).containsKey(eventName)) {
            return;
        }
        listeners.get(control.cid).get(eventName).remove(listener);
    }

    public void addEventListener(ActiveXEventListener listener) {
        addEventListener(null, listener);
    }

    public void addEventListener(String eventName, ActiveXEventListener listener) {
        addControlEventListener(eventName, this, listener);
    }

    public void removeEventListener(ActiveXEventListener listener) {
        removeControlEventListener(null, this, listener);
    }

    public void removeEventListener(String eventName, ActiveXEventListener listener) {
        removeControlEventListener(eventName, this, listener);
    }

    private static void echo() {
        List<ActiveXEvent> events = new ArrayList<>();
        synchronized (AXLOCK) {
            writeCommand(CMD_ECHO);
            int cnt = readUI16();
            for (int i = 0; i < cnt; i++) {
                long ecid = readUI32();
                String ename = readString();
                int epcount = readUI16();
                Map<String, Object> args = new HashMap<>();
                Map<String, String> argTypes = new HashMap<>();
                for (int j = 0; j < epcount; j++) {
                    String type = readString();
                    Object val = strToValue(type, readString());
                    String key = readString();
                    args.put(key, val);
                    argTypes.put(key, type);
                    readString(); // paramType
                }
                if (instances.containsKey(ecid)) {
                    events.add(new ActiveXEvent(instances.get(ecid), ename, args, argTypes));
                }
            }
        }

        for (ActiveXEvent ev : events) {
            if (listeners.containsKey(ev.source.cid)) {
                for (String evName : listeners.get(ev.source.cid).keySet()) {
                    if (evName.equals("*") || evName.equals(ev.name)) {
                        List<ActiveXEventListener> list = listeners.get(ev.source.cid).get(evName);
                        for (ActiveXEventListener l : list) {
                            l.onEvent(ev);
                        }
                    }
                }
            }
        }
    }

    public static String strToClassName(String type) {
        if (type.startsWith("Pointer|Dispatch:")) {
            type = type.substring("Pointer|".length());
        }
        if (type.startsWith("Dispatch:")) {
            String pts[] = type.split(":");
            return pts[1];
        }
        if (type.startsWith("Pointer|Variant")) {
            return "Object";
        }
        if (type.startsWith("Pointer|")) {
            String inc = strToClassName(type.substring("Pointer|".length()));
            if (boxedMap.containsKey(inc)) {
                inc = boxedMap.get(inc);
            }
            return "Reference<" + inc + ">";
        }
        switch (type) {
            case "Empty":
                return null;
            case "Null":
                return null;
            case "Smallint":
                return "int";
            case "Integer":
                return "int";
            case "Single":
                return "float";
            case "Double":
                return "double";
            case "Currency":
                return "BigDecimal";
            case "Date":
                return null;
            case "OleStr":
                return "String";
            case "Dispatch":
                return null; //Unsupported
            case "Error":
                return null; //Unsupported
            case "Boolean":
                return "boolean";
            case "Variant":
                return "Object"; //??
            case "Unknown":
                return "Object"; //Unsupported
            case "Decimal":
                return "BigDecimal";
            case "$0F":
                return null;
            case "ShortInt":
                return "short";
            case "Byte":
                return "int";
            case "Word":
                return "int";
            case "LongWord":
                return "long";
            case "Int64":
                return "BigInteger";
            case "Int":
                return "int";
            case "UInt":
                return "int";
            case "Void":
                return "void";
            case "HResult":
                return "int"; //Unsupported
            case "Pointer":
                return null; //Unsupported
            case "SafeArray":
                return null; //Unsupported
            case "CArray":
                return null; //Unsupported
            case "UserDefined":
                return "Object"; //Unsupported
            case "LPStr":
                return "String";
            case "LPWStr":
                return "String";
            case "IntPtr":
            case "UIntPtr":
            case "FileTime":
            case "Blob":
            case "Stream":
            case "Storage":
            case "StreamedObject":
            case "BlobObject":
            case "CF":
            case "CLSID":
                return null; //Unsupported

            default:
                return null;
        }
    }

    private static Object strToValue(String type, String value) {
        if (type.startsWith("ByRef ")) {
            type = type.substring("ByRef ".length());
        }
        try {
            switch (type) {
                case "Empty":
                    return null;
                case "Null":
                    return null;
                case "Smallint":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Integer.parseInt(value);
                case "Integer":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Integer.parseInt(value);
                case "Single":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Float.parseFloat(value);
                case "Double":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Double.parseDouble(value);
                case "Currency":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return new BigDecimal(value);
                case "Date":
                    return null; //TODO
                case "OleStr":
                    return value;
                case "Dispatch":
                    return null; //Unsupported
                case "Error":
                    return null; //Unsupported
                case "Boolean":
                    return (Boolean) value.equals("True");
                case "Variant":
                    return value; //?
                case "Unknown":
                    return null; //Unsupported
                case "Decimal":
                    return new BigDecimal(value);
                case "$0F":
                    return null;
                case "ShortInt":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Short.parseShort(value);
                case "Byte":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Integer.parseInt(value);
                case "Word":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Integer.parseInt(value);
                case "LongWord":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return Long.parseLong(value);
                case "Int64":
                    if (value.trim().equals("")) {
                        return null;
                    }
                    return new BigInteger(value);
                default:
                    return null;
            }
        } catch (NumberFormatException nfe) {
            System.err.println("WARNING: Invalid " + type + " value: " + value);
            return null;
        }
    }

    /**
     * Gets type of property
     *
     * @param name Property name
     * @return Type class
     */
    public static Property getProperty(String baseguid, String guid, String name) {
        synchronized (AXLOCK) {
            writeTypeCommand(CMD_TYPE_GET_PROPERTY_TYPE, baseguid, guid);
            writeString(name);
            readResult();
            String type = readString();
            readString();
            boolean readable = readString().equals("True");
            readString();
            boolean writable = readString().equals("True");
            return new Property(name, strToClassName(type), type, readable, writable);
        }
    }

    /**
     * Check for method existence
     *
     * @param name Method name
     * @return True when exists
     */
    public static boolean methodExists(String baseguid, String guid, String name) {
        return getMethodNames(baseguid, guid).contains(name);
    }

    /**
     * Get Method instance
     *
     * @param name Method name
     * @return Method object
     */
    public static MethodInfo getMethod(String baseguid, String guid, final String name) {
        if (!methodExists(baseguid, guid, name)) {
            return null;
        }

        final List<String> argNames = new ArrayList<>();
        final List<String> argTypes = new ArrayList<>();
        final List<String> argTypesStr = new ArrayList<>();
        synchronized (AXLOCK) {
            writeTypeCommand(CMD_TYPE_GET_METHOD_PARAMS, baseguid, guid);
            writeString(name);
            readResult();
            final String fname = readString();
            final String returnName = readString();
            final String returnTypeStr = readString();
            final String returnType = strToClassName(returnTypeStr);
            int parameterCount = readUI16();
            final int optionalParameterCount = readUI16();
            for (int i = 0; i < parameterCount; i++) {
                String pname = readString();
                String ptype = readString();
                argNames.add(pname);
                argTypesStr.add(ptype);
                argTypes.add(strToClassName(ptype));
            }
            final String doc = readString();

            return new MethodInfo() {

                @Override
                public int getOptionalArgumentCount() {
                    return optionalParameterCount;
                }

                private String typeToStr(Class c) {
                    if (c == null) {
                        return "Object";
                    }
                    return c.getSimpleName();
                }

                @Override
                public String toString() {
                    String ret = returnType + " " + getName() + "(";
                    for (int i = 0; i < argNames.size(); i++) {
                        if (i > 0) {
                            ret += ", ";
                        }
                        ret += (argTypes.get(i)) + " " + argNames.get(i);
                    }
                    ret += ")";
                    return ret;
                }

                @Override
                public List<String> getArgumentNames() {
                    return argNames;
                }

                @Override
                public String getDoc() {
                    return doc;
                }

                @Override
                public String getReturnName() {
                    return returnName;
                }

                @Override
                public String getReturnTypeStr() {
                    return returnTypeStr;
                }

                @Override
                public String getReturnType() {
                    return returnType;
                }

                @Override
                public List<String> getArgumentTypes() {
                    return argTypes;
                }

                @Override
                public List<String> getArgumentTypesStr() {
                    return argTypesStr;
                }

                @Override
                public String getName() {
                    return fname;
                }

                @Override
                public int hashCode() {
                    return name.hashCode();
                }

                @Override
                public boolean equals(Object obj) {
                    if (obj == null) {
                        return false;
                    }
                    if (!(obj instanceof MethodInfo)) {
                        return false;
                    }
                    MethodInfo m = (MethodInfo) obj;

                    return getName().equals(m.getName());
                }

            };
        }
    }

    private void writeCid() {
        writeUI32(cid);
        String type = readString();
        String val = readString();
        if (type.equals("Error")) {
            throw new ActiveXException(val);
        }
    }

    private static void writeTypeCommand(int command, String baseguid, String guid) {
        writeCommand(command);
        writeString(baseguid);
        writeString(guid);
    }

    private void writeComponentCommand(int command) {
        writeCommand(command);
        writeCid();
    }

    /**
     * Gets property value
     *
     * @param name Property name
     * @return Value of property
     */
    public Object getPropertyValue(Class c, String name) {
        synchronized (AXLOCK) {
            writeComponentCommand(CMD_OBJ_GET_PROPERTY);
            writeString(name);
            GUID g = (GUID) c.getAnnotation(GUID.class);
            if (g != null) {
                writeString("Dispatch");
                writeString(g.base().equals("") ? g.value() : g.base());
                writeString(g.value());
            } else {
                writeString("String");
            }
            readResult();
            String type = readString();
            if (type.equals("Dispatch")) {
                long cid = readUI32();
                return ActiveX.getObject(c, cid);
            }
            String val = readString();
            return strToValue(type, val);
        }
    }

    private static void readResult() {
        String type = readString();
        String val = readString();
        if ("Error".equals(type)) {
            throw new ActiveXException(val);
        }
    }

    /**
     * Sets property value
     *
     * @param name Property name
     * @param value New value
     */
    public void setPropertyValue(Class c, String name, Object value) {
        synchronized (AXLOCK) {
            writeComponentCommand(CMD_OBJ_SET_PROPERTY);
            writeString(name);
            GUID g = (GUID) c.getAnnotation(GUID.class);
            if (g != null && (value instanceof ICOMInstance)) {
                writeString("Dispatch");
                writeUI32(((ICOMInstance) value).getCid());
                writeString(g.base().equals("") ? g.value() : g.base());
                writeString(g.value());
            } else {
                writeString("String");
            }
            writeString("" + value);
            readResult();
        }
    }

    /**
     * Retrieves all classes in OCX file
     *
     * @param ocx OCX file
     * @return List of classinfos
     */
    public static List<ClassInfo> getOcxClasses(File ocx) {
        List<ClassInfo> ret = new ArrayList<>();
        writeCommand(CMD_GET_OCX_CLASSES);
        writeString(ocx.getAbsolutePath());
        readResult();
        int cnt = readUI16();
        for (int i = 0; i < cnt; i++) {
            String name = readString();
            String docstring = readString();
            String baseguid = readString();
            String guid = baseguid;
            ret.add(new ClassInfo(name, docstring, baseguid, guid, ocx));
        }
        return ret;
    }

    /**
     * Creates ActiveX control
     *
     * @param guid ClassId - GUID
     */
    public ActiveXControl(String baseguid, String guid, Panel panel) {
        this("", baseguid, guid, panel);
    }

    /**
     * Creates ActiveX control from file
     *
     * @param filename File to create class from
     * @param guid ClassId - GUID
     */
    public ActiveXControl(File filename, String baseguid, String guid, Panel panel) {
        this(filename.getAbsolutePath(), baseguid, guid, panel);
    }

    /**
     * Gets ClassId GUID
     *
     * @return
     */
    public String getGuid() {
        return guid;
    }

    /**
     * Gets Documentation String for class
     *
     * @return
     */
    public String getDocString() {
        return docString;
    }

    private ActiveXControl(long cid) {
        this.cid = cid;
        instances.put(cid, this);
    }

    public static ActiveXControl getInstance(long cid) {
        if (instances.containsKey(cid)) {
            return instances.get(cid);
        }
        return new ActiveXControl(cid);
    }

    /**
     * Creates ActiveX control from file path
     *
     * @param filename File path to create class from
     * @param guid ClassId - GUID
     */
    public ActiveXControl(String filename, String baseguid, String guid, Panel panel) {
        this.guid = guid;
        this.panel = panel;
        synchronized (AXLOCK) {
            writeCommand(CMD_NEW);
            writeString(filename);
            writeString(baseguid);
            writeString(guid);
            readResult();
            cid = readUI32();
            this.guid = readString();
            progId = readString();
            className = readString();
            docString = readString();
        }
        instances.put(cid, this);
        if (panel != null) {
            panel.addComponentListener(new ComponentListener() {
                @Override
                public void componentResized(ComponentEvent e) {
                    resize();
                }

                @Override
                public void componentMoved(ComponentEvent e) {
                }

                @Override
                public void componentShown(ComponentEvent e) {
                    componentResized(e);
                }

                @Override
                public void componentHidden(ComponentEvent e) {
                }
            });
        };
    }

    private static int shiftStateToModifiers(int shiftState) {
        boolean shiftDown = (shiftState & 1) == 1;
        boolean ctrlDown = (shiftState & 2) == 2;
        boolean altDown = (shiftState & 4) == 4;
        int modifiers = (shiftDown ? Event.SHIFT_MASK : 0) + (ctrlDown ? Event.CTRL_MASK : 0) + (altDown ? Event.ALT_MASK : 0);
        return modifiers;
    }

    private void attach() {
        synchronized (AXLOCK) {
            writeComponentCommand(CMD_OBJ_SET_PARENT);
            hwnd = Native.getComponentPointer(panel).hashCode();
            writeUI32(hwnd);

            //Pass mouse move event
            ActiveXEventListener mouseHandler = new ActiveXEventListener() {
                @Override
                public void onEvent(ActiveXEvent ev) {
                    int fX = (Integer) ev.args.get("fX");
                    int fY = (Integer) ev.args.get("fX");
                    int button = (Integer) ev.args.get("nButton");
                    int shiftState = (Integer) ev.args.get("nShiftState");

                    boolean buttonLeft = (button & 1) == 1;
                    boolean buttonRight = (button & 2) == 2;
                    boolean buttonMiddle = (button & 4) == 4;

                    int oneButton = buttonLeft ? 1 : (buttonRight ? 2 : (buttonMiddle ? 3 : 0));

                    int clickCount = 0;
                    int eventType = 0;
                    switch (ev.name) {
                        case "MouseMove":
                            eventType = MouseEvent.MOUSE_MOVED;
                            break;
                        case "MouseUp":
                            eventType = MouseEvent.MOUSE_RELEASED;
                            break;
                        case "MouseDown":
                            eventType = MouseEvent.MOUSE_PRESSED;
                            break;
                        case "Click":
                            eventType = MouseEvent.MOUSE_CLICKED;
                            clickCount = 1;
                            break;
                        case "DoubleClick":
                            eventType = MouseEvent.MOUSE_CLICKED;
                            clickCount = 2;
                            break;
                    }

                    panel.dispatchEvent(new MouseEvent(panel, eventType, System.currentTimeMillis(), shiftStateToModifiers(shiftState), fX, fY, clickCount, false, oneButton));
                }
            };

            addEventListener("MouseMove", mouseHandler);
            addEventListener("MouseUp", mouseHandler);
            addEventListener("MouseDown", mouseHandler);
            addEventListener("Click", mouseHandler);
            addEventListener("DoubleClick", mouseHandler);

            ActiveXEventListener keyHandler = new ActiveXEventListener() {

                @Override
                public void onEvent(ActiveXEvent ev) {
                    int nKeyCode = 0;
                    int nShiftState = 0;
                    int eventType = 0;
                    int nKeyAscii = 0;

                    switch (ev.name) {
                        case "KeyDown":
                        case "KeyUp":
                            nKeyCode = (Integer) ev.args.get("nKeyCode");
                            nShiftState = (Integer) ev.args.get("nShiftState");
                            break;
                        case "KeyPress":
                            nKeyAscii = (Integer) ev.args.get("nKeyAscii");
                            break;
                    }
                    switch (ev.name) {
                        case "KeyDown":
                            eventType = KeyEvent.KEY_PRESSED;
                            break;
                        case "KeyUp":
                            eventType = KeyEvent.KEY_RELEASED;
                            break;
                        case "KeyPress":
                            eventType = KeyEvent.KEY_TYPED;
                            break;
                    }
                    panel.dispatchEvent(new KeyEvent(panel, eventType, System.currentTimeMillis(), shiftStateToModifiers(nShiftState), nKeyCode, (char) nKeyAscii));
                }
            };
            addEventListener("KeyUp", keyHandler);
            addEventListener("KeyDown", keyHandler);
            addEventListener("KeyPress", keyHandler);

            attached = true;
        }
    }

    private void resize() {
        if (!attached) {
            attach();
        }
        synchronized (AXLOCK) {
            writeComponentCommand(CMD_OBJ_RESIZE);
            writeUI16(panel.getWidth());
            writeUI16(panel.getHeight());
        }
    }

    /**
     * Retrieves all available property names
     *
     * @return
     */
    public static List<String> getPropertyNames(String baseguid, String guid) {
        synchronized (AXLOCK) {
            writeTypeCommand(CMD_TYPE_LIST_PROPERTIES, baseguid, guid);
            readResult();
            return readStrings();
        }
    }

    /**
     * Retrieves all available method names
     *
     * @return
     */
    public static Set<String> getMethodNames(String baseguid, String guid) {
        synchronized (AXLOCK) {
            writeTypeCommand(CMD_TYPE_LIST_METHODS, baseguid, guid);
            readResult();
            return new HashSet<>(readStrings());
        }
    }

    /**
     * Retrieves all available event names
     *
     * @return
     */
    public static List<String> getEventNames(String baseguid, String guid) {
        synchronized (AXLOCK) {
            writeTypeCommand(CMD_TYPE_LIST_EVENTS, baseguid, guid);
            readResult();
            return readStrings();
        }
    }

    /**
     * Call method on object with array arguments
     *
     * @param methodName Method name
     * @param args Call arguments
     * @return Return value of method
     */
    public <E> E callMethodArr(String methodName, Object[] args, Class[] paramTypes, Type[] genericParamTypes, Class<E> retType) {
        synchronized (AXLOCK) {
            writeComponentCommand(CMD_OBJ_CALL_METHOD);
            writeString(methodName);
            writeUI16(args.length);
            for (int i = 0; i < args.length; i++) {

                Object o = args[i];
                Class c = paramTypes[i];
                if (Reference.class.isAssignableFrom(c)) {
                    o = ((Reference) o).getVal();
                    ParameterizedType s = (ParameterizedType) genericParamTypes[i];
                    c = (Class) s.getActualTypeArguments()[0];
                    writeString("Reference");
                }
                GUID g = (GUID) c.getAnnotation(GUID.class);

                if (g != null) {
                    writeString("Object");
                    writeString(g.base().equals("") ? g.value() : g.base());
                    writeString(g.value());
                    writeUI32(((ICOMInstance) o).getCid());
                } else {
                    writeString("String");
                    writeString("" + o);
                }
            }
            Class c = retType;
            if (Reference.class.isAssignableFrom(c)) {
                ParameterizedType referenceType = (ParameterizedType) c.getGenericSuperclass();
                c = (Class<?>) referenceType.getActualTypeArguments()[0];
                writeString("Reference");
            }
            if (ICOMInstance.class.isAssignableFrom(c)) {
                writeString("Object");
                GUID g = (GUID) c.getAnnotation(GUID.class);
                if (g == null) {
                    throw new ActiveXException("No GUID");
                }
                writeString(g.base().equals("") ? g.value() : g.base());
                writeString(g.value());
            } else {
                writeString("String");
            }

            readResult();

            for (int i = 0; i < args.length; i++) {
                c = paramTypes[i];
                Object o = args[i];
                if (Reference.class.isAssignableFrom(c)) {
                    String type = readString();
                    if (type.equals("Dispatch")) {
                        long cid = readUI32();
                        ((Reference) o).setVal(ActiveX.getObject(retType, cid));
                    } else {
                        String val = readString();
                        ((Reference) o).setVal(strToValue(type, val));
                    }
                }
            }

            String type = readString();
            if (type.equals("Dispatch")) {
                long cid = readUI32();
                return ActiveX.getObject(retType, cid);
            } else {
                String val = readString();
                return (E) strToValue(type, val);
            }
        }
    }

    private static List<String> readStrings() {
        int len = readUI16();
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            ret.add(readString());
        }
        return ret;
    }

    private static String readString() {
        int len = (int) readUI32();
        byte data[] = new byte[len];
        read(data);
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return new String(data);
        }
    }

    private static int readUI16() {
        byte data[] = new byte[2];
        read(data);
        return ((data[0] & 0xff) << 8) + (data[1] & 0xff);
    }

    private static long readUI32() {
        byte data[] = new byte[4];
        read(data);
        return ((data[0] & 0xff) << 24) + ((data[1] & 0xff) << 16) + ((data[2] & 0xff) << 8) + (data[3] & 0xff);
    }

    private static void writeCommand(int cmd) {
        writeUI8(cmd);
    }

    private static int readUI8() {
        byte data[] = new byte[1];
        read(data);
        return data[0] & 0xff;
    }

    private static int read(byte res[]) {
        final IntByReference ibr = new IntByReference();
        int read = 0;
        while (read < res.length) {
            byte[] data = new byte[res.length - read];
            boolean result = Kernel32.INSTANCE.ReadFile(pipe, data, data.length, ibr, null);
            if (!result) {
                return Kernel32.INSTANCE.GetLastError();
            }
            int readNow = ibr.getValue();
            System.arraycopy(data, 0, res, read, readNow);
            read += readNow;
        }
        return 0;
    }

    private static void writeUI8(int val) {
        write(new byte[]{(byte) val});
    }

    private static void writeUI32(long val) {
        write(new byte[]{
            (byte) ((val >> 24) & 0xff),
            (byte) ((val >> 16) & 0xff),
            (byte) ((val >> 8) & 0xff),
            (byte) ((val) & 0xff)
        });
    }

    private static void writeUI16(long val) {
        write(new byte[]{
            (byte) ((val >> 8) & 0xff),
            (byte) ((val) & 0xff)
        });
    }

    private static void writeString(String s) {
        byte data[];
        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            data = s.getBytes();
        }
        writeUI32(data.length);
        write(data);
    }

    private static int write(byte data[]) {
        IntByReference ibr = new IntByReference();
        boolean result = Kernel32.INSTANCE.WriteFile(pipe, data, data.length, ibr, null);
        if (!result) {
            return Kernel32.INSTANCE.GetLastError();
        }
        if (ibr.getValue() != data.length) {
            return -1;
        }
        return 0;
    }

    /**
     * Converts ProgId to suitable Java class name
     *
     * @param progId ProgId
     * @return
     */
    public static String progIdToJavaClassName(String progId) {
        String controlName = null;

        if (!progId.isEmpty()) {
            String parts[] = progId.split("\\.");
            if (parts.length >= 2) {
                if (parts[1].equals(parts[0])) {
                    controlName = parts[1];
                } else {
                    controlName = parts[0] + "_" + parts[1];
                }
            }
        }
        return controlName;
    }

    /**
     * Converts ProgId of this class to suitable Java class name
     *
     * @return
     */
    public static String getClassName(String baseguid, String guid) {
        synchronized (AXLOCK) {
            writeTypeCommand(CMD_TYPE_GET_INFO, baseguid, guid);
            readResult();
            String name = readString();
            readString();
            return name;
        }
    }

    public static String getClassDoc(String baseguid, String guid) {
        synchronized (AXLOCK) {
            writeTypeCommand(CMD_TYPE_GET_INFO, baseguid, guid);
            readResult();
            readString();
            String docString = readString();
            return docString;
        }
    }

    private static String firstToUpperCase(String s) {
        if (s == null) {
            return s;
        }
        if (s.isEmpty()) {
            return s;
        }
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }

    private static String typeToStr(String c) {
        if (c == null) {
            return "Object";
        }
        return c;
    }

    private static void parseNeededGuid(String type, Set<String> neededGuids) {
        if (type == null) {
            return;
        }
        String pts[];
        if (!type.contains("|")) {
            pts = new String[]{type};
        } else {
            pts = type.split("\\|");
        }
        for (String p : pts) {
            if (p.startsWith("Dispatch:") || p.startsWith("Unknown:")) {
                pts = p.split(":");
                neededGuids.add(pts[2] + ":" + pts[3]);
            }
        }
    }

    /**
     * Generates Java Definition from this class
     *
     * @return
     */
    public static String getJavaDefinition(String baseguid, String guid, Set<String> neededGuids, String javaPackage) {
        StringBuilder sb = new StringBuilder();
        String nl = System.lineSeparator();
        String controlName = getClassName(baseguid, guid);

        String docString = getClassDoc(baseguid, guid);

        if (controlName == null) {
            controlName = "MyClass";
        }
        sb.append("package ").append(javaPackage).append(";").append(nl).append(nl);
        sb.append("import com.jpexs.javactivex.*;").append(nl);
        sb.append(nl);
        sb.append("/**").append(nl);
        if (docString.isEmpty()) {
            sb.append(" * Class ").append(controlName).append(nl);
        }
        sb.append(" * ").append(docString).append(nl);
        sb.append(" */").append(nl);
        if (baseguid == null || baseguid.equals("") || baseguid.equals(guid)) {
            sb.append("@GUID(\"").append(guid).append("\")").append(nl);
        } else {
            sb.append("@GUID(value=\"").append(guid).append("\", base=\"").append(baseguid).append("\")").append(nl);
        }

        sb.append("public interface ").append(controlName).append(" {").append(nl);

        sb.append(nl);

        Set<String> methodNames = getMethodNames(baseguid, guid);

        String hiddenMethods[] = new String[]{
            "AddRef", "Release", "QueryInterface", //IUnknown
            "GetIDsOfNames", "GetTypeInfo", "GetTypeInfoCount", "Invoke" //IDispatch
        };

        List<String> events = getEventNames(baseguid, guid);
        for (String event : events) {
            sb.append(nl);
            sb.append("\t/**").append(nl);
            sb.append("\t * Adds ").append(event).append(" event listener").append(nl);
            sb.append("\t * @param l Event listener").append(nl);
            sb.append("\t */").append(nl);
            sb.append("\t@AddListener(\"").append(event).append("\")").append(nl);
            sb.append("\tpublic void add").append(firstToUpperCase(event)).append("Listener(ActiveXEventListener l);").append(nl);

            sb.append(nl);
            sb.append("\t/**").append(nl);
            sb.append("\t * Removes ").append(event).append(" event listener").append(nl);
            sb.append("\t * @param l Event listener").append(nl);
            sb.append("\t */").append(nl);
            sb.append("\t@RemoveListener(\"").append(event).append("\")").append(nl);
            sb.append("\tpublic void remove").append(firstToUpperCase(event)).append("Listener(ActiveXEventListener l);").append(nl);
        }

        for (String metName : methodNames) {
            MethodInfo m = getMethod(baseguid, guid, metName);
            String doc = m.getDoc();

            if (Arrays.asList(hiddenMethods).contains(metName)) {
                continue;
            }

            int optcnt = m.getOptionalArgumentCount();
            for (int k = 0; k <= optcnt; k++) {
                sb.append(nl);
                sb.append("\t/**").append(nl);
                if (!doc.isEmpty()) {
                    sb.append("\t * ").append(doc.trim().replaceAll("\r\n|\r|\n", "\t * ")).append(nl);
                } else {
                    sb.append("\t * Method ").append(metName).append(nl);
                }
                sb.append("\t * ").append(nl);

                List<String> argnames = m.getArgumentNames();
                List<String> argtypes = m.getArgumentTypes();
                List<String> argtypesstr = m.getArgumentTypesStr();
                for (int i = 0; i < argnames.size() - k; i++) {
                    parseNeededGuid(argtypesstr.get(i), neededGuids);
                    sb.append("\t * @param ").append(argnames.get(i));
                    if (argtypes.get(i) == null) {
                        sb.append(" (").append(argtypesstr.get(i)).append(") ");
                    }
                    sb.append(nl);
                }
                String retType = m.getReturnType();

                parseNeededGuid(m.getReturnTypeStr(), neededGuids);

                if (!"void".equals(retType)) {
                    sb.append("\t * @return");
                    if (retType == null) {
                        sb.append(" (").append(m.getReturnTypeStr()).append(")");
                    }
                    sb.append(nl);
                }
                sb.append("\t */").append(nl);
                sb.append("\tpublic ");

                sb.append(typeToStr(m.getReturnType())).append(" ").append(m.getName()).append("(");
                for (int i = 0; i < argnames.size() - k; i++) {
                    if (i > 0) {
                        sb.append(", ");
                    }
                    sb.append(typeToStr(argtypes.get(i))).append(" ").append(argnames.get(i));
                }
                sb.append(")");

                sb.append(";").append(nl).append(nl);
            }

        }

        List<String> propNames = getPropertyNames(baseguid, guid);

        for (String propName : propNames) {
            Property p = getProperty(baseguid, guid, propName);
            parseNeededGuid(p.typeStr, neededGuids);
            String type = strToClassName(p.typeStr);
            boolean customType = false;
            if (type == null) {
                type = "Object";
                customType = true;
            }
            if (p.readable) {
                sb.append(nl);
                sb.append("\t/**").append(nl);
                sb.append("\t * Getter for property ").append(propName).append(nl);
                sb.append("\t * ").append(nl);
                sb.append("\t * @return ");
                if (customType) {
                    sb.append("(").append(p.typeStr).append(") ");
                }
                sb.append(propName).append(" value").append(nl);
                sb.append("\t */").append(nl);
                sb.append("\t@Getter(\"").append(propName).append("\")").append(nl);
                sb.append("\tpublic ").append(typeToStr(type)).append(" get").append(firstToUpperCase(propName)).append(methodExists(baseguid, guid, "get" + firstToUpperCase(propName)) ? "_" : "").append("();").append(nl);
            }

            if (p.writable) {
                sb.append(nl);
                sb.append("\t/**").append(nl);
                sb.append("\t * Setter for property ").append(propName).append(nl);
                sb.append("\t * ").append(nl);
                sb.append("\t * @param value ");
                if (customType) {
                    sb.append("(").append(p.typeStr).append(") ");
                }
                sb.append("New ").append(propName).append(" value").append(nl);
                sb.append("\t */").append(nl);
                sb.append("\t@Setter(\"").append(propName).append("\")").append(nl);
                sb.append("\tpublic void set").append(firstToUpperCase(propName)).append(methodExists(baseguid, guid, "set" + firstToUpperCase(propName)) ? "_" : "").append("(").append(type).append(" value);").append(nl);
            }
        }

        sb.append("}");

        return sb.toString();
    }

    /**
     * Gets ProgId
     *
     * @return
     */
    public String getProgId() {
        return progId;
    }

    /**
     * Retrieves all registered classes
     *
     * @return
     */
    public static List<ClassInfo> getRegisteredClasses() {
        List<ClassInfo> ret = new ArrayList<>();
        synchronized (AXLOCK) {
            writeCommand(CMD_GET_REGISTERED_CLASSES);
            int cnt = readUI16();
            for (int i = 0; i < cnt; i++) {

                String name = readString();
                String docstring = readString();
                String baseguid = readString();
                String guid = baseguid;
                File file = new File(readString());
                ret.add(new ClassInfo(name, docstring, baseguid, guid, file));
            }
        }
        return ret;
    }

    @Override
    protected void finalize() throws Throwable {
        dispose();
        super.finalize();
    }

    /**
     * Destroys the component
     */
    public void dispose() {
        if (!disposed) {
            synchronized (AXLOCK) {
                writeComponentCommand(CMD_OBJ_DESTROY);
            }
            disposed = true;
        }
    }

}
