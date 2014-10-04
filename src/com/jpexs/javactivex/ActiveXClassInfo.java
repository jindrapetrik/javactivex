package com.jpexs.javactivex;

import java.io.File;

/**
 * ActiveX class information
 *
 * @author JPEXS
 */
public class ActiveXClassInfo {

    /**
     * Class Id (GUID)
     */
    public String guid;
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

    public ActiveXClassInfo(String name, String docString, String guid, File file) {
        this.guid = guid;
        this.docString = docString;
        this.name = name;
        this.file = file;
    }

    @Override
    public String toString() {
        return "[Class Name=\"" + name + "\" GUID=\"" + guid + "\" DocString=\"" + docString + "\" File=\"" + file.getAbsolutePath() + "\"]";
    }

    /**
     * Creates ActiveX control instance
     *
     * @return
     */
    public ActiveXControl createControl() {
        return new ActiveXControl(file, guid);
    }

    /**
     * Generates Java definition file
     *
     * @return
     */
    public String getJavaDefinition() {
        return ActiveXControl.generateJavaDefinition(guid);
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
