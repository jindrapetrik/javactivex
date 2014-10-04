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
import java.awt.Color;
import java.awt.Panel;
import java.awt.event.ComponentEvent;
import java.awt.event.ComponentListener;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

/**
 * ActiveX control Component
 *
 * @author JPEXS
 */
public class ActiveXControl extends Panel {

    private static WinNT.HANDLE pipe;
    private static WinNT.HANDLE process;
    private static final int CMD_ECHO = 0;
    private static final int CMD_NEW = 1;
    private static final int CMD_DESTROY = 2;
    private static final int CMD_DESTROYALL = 3;
    private static final int CMD_LIST_PROPERTIES = 4;
    private static final int CMD_LIST_METHODS = 5;
    private static final int CMD_LIST_EVENTS = 6;
    private static final int CMD_RESIZE = 7;
    private static final int CMD_GET_PROPERTY = 8;
    private static final int CMD_SET_PROPERTY = 9;
    private static final int CMD_SET_PARENT = 10;
    private static final int CMD_CALL_METHOD = 11;
    private static final int CMD_GET_METHOD_PARAMS = 12;
    private static final int CMD_GET_OCX_CLASSES = 13;
    private static final int CMD_GET_REGISTERED_CLASSES = 14;

    private int hwnd;
    private String guid;
    private String docString;
    private long cid = -1;
    private String progId;
    private boolean shown = false;

    private static final int ECHO_INTERVAL = 100;

    private final static Map<Class<?>, Class<?>> boxedMap = new HashMap<>();
    private static Timer syncTimer = new Timer();

    static {

        if (!Platform.isWindows()) {
            throw new UnsupportedOperationException("Active X is available on Windows only.");
        }

        boxedMap.put(boolean.class, Boolean.class);
        boxedMap.put(byte.class, Byte.class);
        boxedMap.put(short.class, Short.class);
        boxedMap.put(char.class, Character.class);
        boxedMap.put(int.class, Integer.class);
        boxedMap.put(long.class, Long.class);
        boxedMap.put(float.class, Float.class);
        boxedMap.put(double.class, Double.class);

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
        String exePath = appDir + "lib\\ActiveXServer.exe";

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

        syncTimer.schedule(new TimerTask() {

            @Override
            public void run() {
                try {
                    echo();
                } catch (Exception ex) {
                    if (syncTimer != null) {
                        syncTimer.cancel();
                        syncTimer = null;
                    }
                }
            }
        }, ECHO_INTERVAL, ECHO_INTERVAL);

        Runtime.getRuntime().addShutdownHook(new Thread() {

            @Override
            public void run() {
                writeCommand(CMD_DESTROYALL);
                Kernel32.INSTANCE.CloseHandle(pipe);
                Kernel32.INSTANCE.TerminateProcess(process, 0);
            }

        });
    }

    private static void echo() {
        synchronized (ActiveXControl.class) {
            writeCommand(CMD_ECHO);
            String ok = readString();
            if (!ok.equals("OK")) {
                throw new ActiveXException("Sync failed - result:" + ok);
            }
        }
    }

    private static Class strToClass(String type) {
        switch (type) {
            case "Empty":
                return null;
            case "Null":
                return null;
            case "Smallint":
                return int.class;
            case "Integer":
                return int.class;
            case "Single":
                return float.class;
            case "Double":
                return double.class;
            case "Currency":
                return BigDecimal.class;
            case "Date":
                return null;
            case "OleStr":
                return String.class;
            case "Dispatch":
                return null; //Unsupported
            case "Error":
                return null; //Unsupported
            case "Boolean":
                return boolean.class;
            case "Variant":
                return String.class; //??
            case "Unknown":
                return null; //Unsupported
            case "Decimal":
                return BigDecimal.class;
            case "$0F":
                return null;
            case "ShortInt":
                return short.class;
            case "Byte":
                return Integer.class;
            case "Word":
                return Integer.class;
            case "LongWord":
                return long.class;
            case "Int64":
                return BigInteger.class;
            //dalsi
            case "Int":
                return int.class;
            case "UInt":
                return int.class;
            case "Void":
                return void.class;
            case "HResult":
                return null; //Unsupported
            case "Pointer":
                return null; //Unsupported
            case "SafeArray":
                return null; //Unsupported
            case "CArray":
                return null; //Unsupported
            case "UserDefined":
                return null; //Unsupported
            case "LPStr":
                return String.class;
            case "LPWStr":
                return String.class;
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
        switch (type) {
            case "Empty":
                return null;
            case "Null":
                return null;
            case "Smallint":
                return Integer.parseInt(value);
            case "Integer":
                return Integer.parseInt(value);
            case "Single":
                return Float.parseFloat(value);
            case "Double":
                return Double.parseDouble(value);
            case "Currency":
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
                return Short.parseShort(value);
            case "Byte":
                return Integer.parseInt(value);
            case "Word":
                return Integer.parseInt(value);
            case "LongWord":
                return Long.parseLong(value);
            case "Int64":
                return new BigInteger(value);
            default:
                return null;
        }
    }

    /**
     * Gets type of property
     *
     * @param name Property name
     * @return Type class
     */
    public Class getPropertyType(String name) {
        synchronized (ActiveXControl.class) {
            writeComponentCommand(CMD_GET_PROPERTY);
            writeString(name);
            String type = readString();
            String val = readString();
            if ("Error".equals(type)) {
                throw new ActiveXException(val);
            }
            return strToClass(type);
        }
    }

    /**
     * Check for method existence
     *
     * @param name Method name
     * @return True when exists
     */
    public boolean methodExists(String name) {
        return getMethodNames().contains(name);
    }

    /**
     * Get Method instance
     *
     * @param name Method name
     * @return Method object
     */
    public synchronized ActiveXMethodInfo getMethod(final String name) {
        if (!methodExists(name)) {
            return null;
        }

        final List<String> argNames = new ArrayList<>();
        final List<Class> argTypes = new ArrayList<>();

        writeComponentCommand(CMD_GET_METHOD_PARAMS);
        writeString(name);
        readResult();
        final String fname = readString();
        final String returnName = readString();
        final Class returnType = strToClass(readString());
        int parameterCount = readUI16();
        for (int i = 0; i < parameterCount; i++) {
            String pname = readString();
            String ptype = readString();
            argNames.add(pname);
            argTypes.add(strToClass(ptype));
        }
        final String doc = readString();

        return new ActiveXMethodInfo() {

            private String typeToStr(Class c) {
                if (c == null) {
                    return "Object";
                }
                return c.getSimpleName();
            }

            @Override
            public String toString() {
                String ret = typeToStr(returnType) + " " + getName() + "(";
                for (int i = 0; i < argNames.size(); i++) {
                    if (i > 0) {
                        ret += ", ";
                    }
                    ret += typeToStr(argTypes.get(i)) + " " + argNames.get(i);
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
            public Object getOwner() {
                return ActiveXControl.this;
            }

            @Override
            public Class getReturnType() {
                return returnType;
            }

            @Override
            public List<Class> getArgumentTypes() {
                return argTypes;
            }

            @Override
            public Object call(Object... args) {
                return callMethodArr(name, args);
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
                if (!(obj instanceof ActiveXMethodInfo)) {
                    return false;
                }
                ActiveXMethodInfo m = (ActiveXMethodInfo) obj;

                return getName().equals(m.getName());
            }

        };
    }

    private synchronized void writeCid() {
        writeUI32(cid);
        String type = readString();
        String val = readString();
        if (type.equals("Error")) {
            throw new ActiveXException(val);
        }
    }

    private synchronized void writeComponentCommand(int command) {
        writeCommand(command);
        writeCid();
    }

    /**
     * Gets property value
     *
     * @param name Property name
     * @return Value of property
     */
    public Object getProperty(String name) {
        synchronized (ActiveXControl.class) {
            writeComponentCommand(CMD_GET_PROPERTY);
            writeString(name);
            String type = readString();
            String val = readString();
            if ("Error".equals(type)) {
                throw new ActiveXException(val);
            }
            return strToValue(type, val);
        }
    }

    private static synchronized void readResult() {
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
    public void setProperty(String name, Object value) {
        synchronized (ActiveXControl.class) {
            writeComponentCommand(CMD_SET_PROPERTY);
            writeString(name);
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
    public static List<ActiveXClassInfo> getOcxClasses(File ocx) {
        List<ActiveXClassInfo> ret = new ArrayList<>();
        writeCommand(CMD_GET_OCX_CLASSES);
        writeString(ocx.getAbsolutePath());
        readResult();
        int cnt = readUI16();
        for (int i = 0; i < cnt; i++) {
            ret.add(new ActiveXClassInfo(readString(), readString(), readString(), ocx));
        }
        return ret;
    }

    /**
     * Creates ActiveX control
     *
     * @param guid ClassId - GUID
     */
    public ActiveXControl(String guid) {
        this("", guid);
    }

    /**
     * Creates ActiveX control from file
     *
     * @param filename File to create class from
     * @param guid ClassId - GUID
     */
    public ActiveXControl(File filename, String guid) {
        this(filename.getAbsolutePath(), guid);
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

    /**
     * Creates ActiveX control from file path
     *
     * @param filename File path to create class from
     * @param guid ClassId - GUID
     */
    public ActiveXControl(String filename, String guid) {
        this.guid = guid;
        writeCommand(CMD_NEW);
        writeString(filename);
        writeString(guid);
        readResult();
        cid = readUI32();
        this.guid = readString();
        progId = readString();
        docString = readString();
        setBackground(Color.green);
        addComponentListener(new ComponentListener() {
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
    }

    private void resize() {
        synchronized (ActiveXControl.class) {
            if (!shown) {
                writeComponentCommand(CMD_SET_PARENT);
                hwnd = Native.getComponentPointer(ActiveXControl.this).hashCode();
                writeUI32(hwnd);
                shown = true;
            }
            writeComponentCommand(CMD_RESIZE);
            writeUI16(getWidth());
            writeUI16(getHeight());
        }
    }

    /**
     * Retrieves all available property names
     *
     * @return
     */
    public List<String> getPropertyNames() {
        synchronized (ActiveXControl.class) {
            writeComponentCommand(CMD_LIST_PROPERTIES);
            return readStrings();
        }
    }

    /**
     * Retrieves all available method names
     *
     * @return
     */
    public List<String> getMethodNames() {
        synchronized (ActiveXControl.class) {
            writeComponentCommand(CMD_LIST_METHODS);
            return readStrings();
        }
    }

    /**
     * Retrieves all available event names
     *
     * @return
     */
    public synchronized List<String> getEventNames() {
        synchronized (ActiveXControl.class) {
            writeComponentCommand(CMD_LIST_EVENTS);
            return readStrings();
        }
    }

    /**
     * Call method on object
     *
     * @param methodName Method name
     * @param args Call arguments
     * @return Return value of method
     */
    public Object callMethod(String methodName, Object... args) {
        synchronized (ActiveXControl.class) {
            return callMethodArr(methodName, args);
        }
    }

    /**
     * Call method on object with array arguments
     *
     * @param methodName Method name
     * @param args Call arguments
     * @return Return value of method
     */
    public synchronized Object callMethodArr(String methodName, Object[] args) {
        writeComponentCommand(CMD_CALL_METHOD);
        writeString(methodName);
        writeUI16(args.length);
        for (Object o : args) {
            writeString("" + o);
        }
        String type = readString();
        String val = readString();
        if ("Error".equals(type)) {
            throw new ActiveXException(val);
        }
        return strToValue(type, val);
    }

    private synchronized static List<String> readStrings() {
        int len = readUI16();
        List<String> ret = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            ret.add(readString());
        }
        return ret;
    }

    private synchronized static String readString() {
        int len = readUI8();
        byte data[] = new byte[len];
        read(data);
        try {
            return new String(data, "UTF-8");
        } catch (UnsupportedEncodingException ex) {
            return new String(data);
        }
    }

    private synchronized static int readUI16() {
        byte data[] = new byte[2];
        read(data);
        return ((data[0] & 0xff) << 8) + (data[1] & 0xff);
    }

    private synchronized static long readUI32() {
        byte data[] = new byte[4];
        read(data);
        return ((data[0] & 0xff) << 24) + ((data[0] & 0xff) << 16) + ((data[0] & 0xff) << 8) + (data[1] & 0xff);
    }

    private synchronized static void writeCommand(int cmd) {
        writeUI8(cmd);
    }

    private synchronized static int readUI8() {
        byte data[] = new byte[1];
        read(data);
        return data[0] & 0xff;
    }

    private synchronized static int read(byte res[]) {
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

    private synchronized static void writeUI8(int val) {
        write(new byte[]{(byte) val});
    }

    private synchronized static void writeUI32(long val) {
        write(new byte[]{
            (byte) ((val >> 24) & 0xff),
            (byte) ((val >> 16) & 0xff),
            (byte) ((val >> 8) & 0xff),
            (byte) ((val) & 0xff)
        });
    }

    private synchronized static void writeUI16(long val) {
        write(new byte[]{
            (byte) ((val >> 8) & 0xff),
            (byte) ((val) & 0xff)
        });
    }

    private synchronized static void writeString(String s) {
        byte data[];
        try {
            data = s.getBytes("UTF-8");
        } catch (UnsupportedEncodingException ex) {
            data = s.getBytes();
        }
        writeUI8(data.length);
        write(data);
    }

    private synchronized static int write(byte data[]) {
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
     * Generates Java Definition from registered Class Id - GUID
     *
     * @param classId Class Id - GUID
     * @return
     */
    public static String generateJavaDefinition(String classId) {
        return generateJavaDefinition("", classId);
    }

    /**
     * Generates Java Definition from OCX file
     *
     * @param ocx Path to OCX file
     * @param classId Class Id - GUID
     * @return
     */
    public static String generateJavaDefinition(String ocx, String classId) {
        return new ActiveXControl(ocx, classId).getJavaDefinition();
    }

    /**
     * Generates Java Definition from OCX file
     *
     * @param ocx OCX file
     * @param classId Class Id - GUID
     * @return
     */
    public static String generateJavaDefinition(File ocx, String classId) {
        return generateJavaDefinition(ocx.getAbsolutePath(), classId);
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
    public String getJavaClassName() {
        return progIdToJavaClassName(progId);
    }

    /**
     * Generates Java Definition from this class
     *
     * @return
     */
    public String getJavaDefinition() {
        String ret = "";
        String nl = System.lineSeparator();
        String controlName = getJavaClassName();

        if (controlName == null) {
            controlName = "MyClass";
        }
        ret += "package com.jpexs.javactivex.controls;" + nl;
        ret += "import com.jpexs.javactivex.ActiveXControl;" + nl;
        ret += "import java.io.File;" + nl;
        ret += nl;
        ret += "/**" + nl;
        ret += " * Class " + progId + nl;
        ret += " * " + docString + nl;
        ret += " */" + nl;
        ret += "public class " + controlName + " extends ActiveXControl {" + nl;

        ret += nl;
        ret += "\t/**" + nl;
        ret += "\t * Constructs class which is already registered" + nl;
        ret += "\t */" + nl;
        ret += "\tpublic " + controlName + "() {" + nl;
        ret += "\t\tthis(\"\");" + nl;
        ret += "\t}" + nl;

        ret += nl;
        ret += "\t/**" + nl;
        ret += "\t * Constructs " + controlName + " from OCX path" + nl;
        ret += "\t * @param ocxPath Path to OCX file which contains " + controlName + " class";
        ret += "\t */" + nl;
        ret += "\tpublic " + controlName + "(String ocxPath) {" + nl;
        ret += "\t\tsuper(ocxPath,\"" + guid + "\");" + nl;
        ret += "\t}" + nl;

        ret += nl;
        ret += "\t/**" + nl;
        ret += "\t * Constructs " + controlName + " from OCX file" + nl;
        ret += "\t * @param ocx OCX file which contains " + controlName + " class";
        ret += "\t */" + nl;
        ret += "\tpublic " + controlName + "(File ocx) {" + nl;
        ret += "\t\tthis(ocx.getAbsolutePath());" + nl;
        ret += "\t}" + nl;

        for (String metName : getMethodNames()) {
            ret += nl;
            ActiveXMethodInfo m = getMethod(metName);
            String doc = m.getDoc();

            ret += "\t/**" + nl;
            if (!doc.isEmpty()) {
                ret += "\t * " + doc.trim().replaceAll("\r\n|\r|\n", "\t * ") + nl;
            } else {
                ret += "\t * Method " + metName + nl;
            }
            for (String pname : m.getArgumentNames()) {
                ret += "\t * @param " + pname + nl;
            }
            Class retType = m.getReturnType();

            if (retType != void.class) {
                ret += "\t * @return" + nl;
            }
            ret += "\t */" + nl;
            ret += "\tpublic " + m.toString() + " {" + nl;
            ret += "\t\t";

            if (retType != void.class) {
                ret += "return ";
                if (retType != null) {
                    if (boxedMap.containsKey(retType)) {
                        ret += "(" + boxedMap.get(retType).getSimpleName() + ")";
                    } else {
                        ret += "(" + retType.getSimpleName() + ")";
                    }
                }
            }
            ret += "callMethod(\"" + metName + "\"";
            for (String pname : m.getArgumentNames()) {
                ret += ", ";
                ret += pname;
            }
            ret += ");" + nl;
            ret += "\t}" + nl;

        }

        for (String propName : getPropertyNames()) {
            Class type = getPropertyType(propName);
            if (type == null) {
                type = Object.class;
            }
            Class coerceType = type;
            if (boxedMap.containsKey(coerceType)) {
                coerceType = boxedMap.get(coerceType);
            }
            ret += nl;
            ret += "\t/**" + nl;
            ret += "\t * Getter for property " + propName + nl;
            ret += "\t * @return " + propName + " value" + nl;
            ret += "\t */" + nl;
            ret += "\tpublic " + type.getSimpleName() + " get" + propName + (methodExists("get" + propName) ? "_" : "") + "() {" + nl;
            ret += "\t\treturn (" + coerceType.getSimpleName() + ")getProperty(\"" + propName + "\");" + nl;
            ret += "\t}" + nl;
            ret += nl;
            ret += "\t/**" + nl;
            ret += "\t * Setter for property " + propName + nl;
            ret += "\t * @param value New " + propName + " value" + nl;
            ret += "\t */" + nl;
            ret += "\tpublic void set" + propName + (methodExists("set" + propName) ? "_" : "") + "(" + type.getSimpleName() + " value) {" + nl;
            ret += "\t\tsetProperty(\"" + propName + "\",value);" + nl;
            ret += "\t}" + nl;
        }

        ret += "}";

        return ret;
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
    public synchronized static List<ActiveXClassInfo> getRegisteredClasses() {
        List<ActiveXClassInfo> ret = new ArrayList<>();
        writeCommand(CMD_GET_REGISTERED_CLASSES);
        int cnt = readUI16();
        for (int i = 0; i < cnt; i++) {
            ret.add(new ActiveXClassInfo(readString(), readString(), readString(), new File(readString())));
        }
        return ret;
    }

}
