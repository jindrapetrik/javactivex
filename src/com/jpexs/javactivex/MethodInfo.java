package com.jpexs.javactivex;

import java.util.List;

/**
 * Interface of a method
 *
 * @author JPEXS
 */
public interface MethodInfo {    

    /**
     * Get name of the method
     *
     * @return
     */
    public String getName();
   
    /**
     * Get return type of the method as Java class
     *
     * @return
     */
    public String getReturnType();
    
    /**
     * Get return type of the method
     *
     * @return
     */
    public String getReturnTypeStr();

    /**
     * Get type of arguments as Java classes
     *
     * @return
     */
    public List<String> getArgumentTypes();

    /**
     * Get type of arguments
     *
     * @return
     */
    public List<String> getArgumentTypesStr();
    
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
    
    /**
     * Get count of optional arguments
     * @return 
     */
    public int getOptionalArgumentCount();
}
