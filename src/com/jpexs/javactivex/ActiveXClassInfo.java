package com.jpexs.javactivex;

import java.awt.Panel;
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
    public ActiveXControl createControl(Panel panel) {
        return new ActiveXControl(file, guid, panel);
    }

    /**
     * Generates Java definition file
     *
     * @return
     */
    public String getJavaDefinition(boolean isGraphicControl) {
        return ActiveXControl.generateJavaDefinition(guid,isGraphicControl);
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
