package com.jpexs.javactivex;

/**
 *
 * @author JPEXS
 */
public class Property {
    public String name;
    public String type;
    public String typeStr;
    public boolean readable;
    public boolean writable;

    public Property(String name, String type, String typeStr, boolean readable, boolean writable) {
        this.type = type;
        this.typeStr = typeStr;
        this.name = name;
        this.type = type;
        this.readable = readable;
        this.writable = writable;
    }
    
}
