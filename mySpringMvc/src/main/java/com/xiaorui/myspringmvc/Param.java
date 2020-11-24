package com.xiaorui.myspringmvc;

import java.lang.annotation.*;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 13:48
 **/
@Target(ElementType.PARAMETER)
@Retention(RetentionPolicy.RUNTIME)
public @interface Param {
    String value();
}
