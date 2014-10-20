package com.jpexs.javactivex;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Marks method to be removeListener for event name
 * @author JPEXS
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RemoveListener {
    /**
     * Event name
     * @return 
     */
    String value();
}
