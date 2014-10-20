package com.jpexs.javactivex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * GUID of COM interface
 * @author JPEXS
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface GUID {
    /**
     * GUID
     * @return 
     */
    String value();
    /**
     * GUID value o base class which is registered
     * @return 
     */
    String base() default "";
}
