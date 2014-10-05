package com.jpexs.javactivex;

/**
 *
 * @author JPEXS
 */
public class ActiveXProperty {
    public String name;
    public Class type;
    public boolean readable;
    public boolean writable;

    public ActiveXProperty(String name, Class type, boolean readable, boolean writable) {
        this.name = name;
        this.type = type;
        this.readable = readable;
        this.writable = writable;
    }
    
}
