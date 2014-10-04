package com.jpexs.javactivex;

import java.util.List;

/**
 * Interface of a method
 *
 * @author JPEXS
 */
public interface ActiveXMethodInfo {

    /**
     * Call the method on its object
     *
     * @param args arguments
     * @return
     */
    public Object call(Object... args);

    /**
     * Get name of the method
     *
     * @return
     */
    public String getName();

    /**
     * Get owner component
     *
     * @return
     */
    public Object getOwner();

    /**
     * Get return type of the method
     *
     * @return
     */
    public Class getReturnType();

    /**
     * Get type of arguments
     *
     * @return
     */
    public List<Class> getArgumentTypes();

    /**
     * Get argument names
     *
     * @return
     */
    public List<String> getArgumentNames();

    /**
     * Get name of return value
     *
     * @return
     */
    public String getReturnName();

    /**
     * Get method documentation
     *
     * @return
     */
    public String getDoc();
}
