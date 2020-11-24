package com.xiaorui.myspringmvc;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 13:28
 **/
public class IndexNotFoundExeption extends RuntimeException {
    public IndexNotFoundExeption(){}
    public IndexNotFoundExeption(String Info){
        super(Info);
    }
}
