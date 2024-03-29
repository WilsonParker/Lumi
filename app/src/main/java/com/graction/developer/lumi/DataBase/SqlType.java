package com.graction.developer.lumi.DataBase;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Graction06 on 2018-01-25.
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface SqlType {
    enum Type{
        String, Double, Long, Integer, Boolean
    }

     Type getType() default Type.String;
}
