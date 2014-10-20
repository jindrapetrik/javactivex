package com.jpexs.javactivex;

/**
 * Class to pass parameters via reference
 * @author JPEXS
 * @param <T> Reference value type
 */
public class Reference<T> {

    private T val;

    public Reference(T val) {
        this.val = val;
    }

    public T getVal() {
        return val;
    }

    public void setVal(T val) {
        this.val = val;
    }

    @Override
    public String toString() {
        return val.toString();
    }

}