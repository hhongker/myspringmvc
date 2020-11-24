package com.xiaorui.myspringmvc;


import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 20:12
 **/
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface PlaceCode {
    PlaceCod[] value();
}
