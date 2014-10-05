package com.jpexs.javactivex;

/**
 *
 * @author JPEXS
 */
public class ActiveXProperty {
    public String name;
    public Class type;
    public String typeStr;
    public boolean readable;
    public boolean writable;

    public ActiveXProperty(String name, Class type, String typeStr, boolean readable, boolean writable) {
        this.type = type;
        this.typeStr = typeStr;
        this.name = name;
        this.type = type;
        this.readable = readable;
        this.writable = writable;
    }
    
}
