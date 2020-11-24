package com.xiaorui.myspringmvc;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 20:28
 **/
public class ControllerMethodContent {

        private Method method;
        private PlaceCod[] placeCods;

    public ControllerMethodContent() {
    }

    public ControllerMethodContent(Method method, PlaceCod[] placeCods) {
        this.method = method;
        this.placeCods = placeCods;
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public PlaceCod[] getPlaceCods() {
        return placeCods;
    }

    public void setPlaceCods(PlaceCod[] placeCods) {
        this.placeCods = placeCods;
    }

    @Override
    public String toString() {
        return "ControllerMethodContent{" +
                "method=" + method +
                ", placeCods=" + placeCods +
                '}';
    }
}
