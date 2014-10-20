package com.jpexs.javactivex;

import java.awt.Panel;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.HashSet;
import java.util.Set;

/**
 * Base tool class for creating COM/ActiveX objects
 * @author JPEXS
 */
public class ActiveX {
    
    
    private static class MyHandler implements InvocationHandler{
        private final ActiveXControl c;
        public MyHandler(String baseguid,String guid, Panel host){
            c = new ActiveXControl(baseguid,guid, host);
        }
        
        public MyHandler(long cid){
            c = ActiveXControl.getInstance(cid);
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            Getter g = method.getAnnotation(Getter.class);
            if(g!=null){
                return c.getPropertyValue(method.getReturnType(),g.value());
            }            
            Setter s = method.getAnnotation(Setter.class);            
            if(s!=null){
                c.setPropertyValue(method.getParameterTypes()[0],s.value(), args[0]);
                return null;
            }                    
            AddListener al = method.getAnnotation(AddListener.class);
            if(al != null){
                c.addEventListener(al.value(),(ActiveXEventListener)args[0]);
                return null;
            }
            
            RemoveListener rl = method.getAnnotation(RemoveListener.class);
            if(al != null){
                c.removeEventListener(rl.value(),(ActiveXEventListener)args[0]);
                return null;
            }
            
            
            
            if(method.getName().equals("getCid")){
                return c.getCid();
            }          
            
            return c.callMethodArr(method.getName(), args==null?new Object[0]:args,method.getParameterTypes(),method.getGenericParameterTypes(),method.getReturnType());
        }
    }
    
    /**
     * Creates COM object
     * @param <E> 
     * @param cls Class which has GUID annotation
     * @return 
     */
    public static <E> E createObject(Class<E> cls){
        return createObject(cls, null);
    }
    
    /**
     * Creates graphic ActiveX control
     * @param <E>
     * @param cls Class which has GUID annotation
     * @param host Panel to host graphic control in
     * @return 
     */
    public static <E> E createObject(Class<E> cls, Panel host){
        GUID g = cls.getAnnotation(GUID.class);
        if(g == null){
            throw new ActiveXException("Interface must have GUID");
        }
        return (E)Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls}, new MyHandler(g.base().equals("")?g.value():g.base(),g.value(), host));
    }
    
    /**
     * Gets existing object
     * @param <E>
     * @param cls Class of the object
     * @param cid Internal component id
     * @return 
     */
    public static <E> E getObject(Class<E> cls, long cid){
        GUID g = cls.getAnnotation(GUID.class);
        if(g == null){
            throw new ActiveXException("Interface must have GUID");
        }
        return (E)Proxy.newProxyInstance(cls.getClassLoader(), new Class[]{cls,ICOMInstance.class}, new MyHandler(cid));
    }
    
    /**
     * Generates interfaces from class id
     * @param clsid GUID of a class
     * @param outdir Directory where to create files
     * @param pkg Java package to put classes in
     */
    public static void generateClassFromTLB(String clsid, String outdir,String pkg){  
        generate(clsid, clsid, new HashSet<String>(),outdir,pkg);
    }
    
    private static void generate(String baseguid,String guid, Set<String> generatedGuids, String outdir, String pkg){    
        if(generatedGuids.contains(baseguid+':'+guid)){
                    return;
        }
        File fdir=new File(outdir);
        if(!fdir.exists()){
            fdir.mkdirs();
        }
        if(!fdir.exists()){
            return;
        }
        generatedGuids.add(baseguid+':'+guid);        
        try(FileOutputStream fos=new FileOutputStream(outdir+File.separator+ActiveXControl.getClassName(baseguid,guid)+".java")) {
            Set<String> neededGuids = new HashSet<>();
            fos.write(ActiveXControl.getJavaDefinition(baseguid,guid,neededGuids,pkg).getBytes("UTF-8"));
            for(String n:neededGuids){
                String pts[] = n.split(":");
                generate(pts[0],pts[1], generatedGuids,outdir,pkg);
            }
        } catch (IOException ex) {
            //ignore
        }          
    }
    
    /**
     * Stops all ActiveX threads
     */
    public static void unload(){
        ActiveXControl.unload();
    }
}
