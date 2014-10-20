package com.jpexs.javactivex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks method to be addlistener for event name
 * @author JPEXS
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AddListener {
    /**
     * Event name
     * @return 
     */
    String value();
}
