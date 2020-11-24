package com.xiaorui.myspringmvc;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 13:39
 **/
public class MethodNotFoundExeption extends RuntimeException{

    public MethodNotFoundExeption() {
    }

    public MethodNotFoundExeption(String message) {
        super(message);
    }
}
