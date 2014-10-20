package com.jpexs.javactivex;

import java.awt.Panel;
import java.io.File;
import java.util.HashSet;

/**
 * ActiveX class information
 *
 * @author JPEXS
 */
public class ClassInfo {

    /**
     * Class Id (GUID)
     */
    public String guid;
    
    /**
     * Id of the base class which is registered
     */
    public String baseguid;
    /**
     * Documentation
     */
    public String docString;
    /**
     * Class name
     */
    public String name;
    /**
     * File containing this class
     */
    public File file;

    public ClassInfo(String name, String docString, String baseGuid, String guid, File file) {
        this.guid = guid;
        this.docString = docString;
        this.name = name;
        this.file = file;
    }

    @Override
    public String toString() {
        return "[Class Name=\"" + name + "\" BaseGUID=\""+baseguid+"\" GUID=\"" + guid + "\" DocString=\"" + docString + "\" File=\"" + file.getAbsolutePath() + "\"]";
    }

    /**
     * Creates ActiveX control instance
     *
     * @return
     */
    public ActiveXControl createControl(Panel panel) {
        return new ActiveXControl(file,baseguid,guid, panel);
    }

    /**
     * Generates Java definition file
     *
     * @return
     */
    public String getJavaDefinition(String pkg) {
        return ActiveXControl.getJavaDefinition(baseguid,guid,new HashSet<String>(),pkg);
    }

    /**
     * Generates suitable java class name
     *
     * @return
     */
    public String getJavaClassName() {
        return ActiveXControl.progIdToJavaClassName(name);
    }
}
