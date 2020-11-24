package com.xiaorui.myspringmvc;

import java.util.HashMap;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 20:00
 **/
public class ForwarContent {
    private String methodReturn;
    private HashMap <String,String> copeAttribute = new HashMap<>();

    public ForwarContent() {
    }

    public ForwarContent(String methodReturn, HashMap<String, String> copeAttribute) {
        this.methodReturn = methodReturn;
        this.copeAttribute = copeAttribute;
    }

    public String getMethodReturn() {
        return methodReturn;
    }

    public void setMethodReturn(String methodReturn) {
        this.methodReturn = methodReturn;
    }

    public HashMap<String, String> getCopeAttribute() {
        return copeAttribute;
    }

    public void setCopeAttribute(HashMap<String, String> copeAttribute) {
        this.copeAttribute = copeAttribute;
    }

    public String getCopeAttributeVlue(String key){
        return this.copeAttribute.get(key);
    }
}

