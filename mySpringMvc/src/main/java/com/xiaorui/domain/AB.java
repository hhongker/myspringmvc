package com.xiaorui.domain;

/**
 * @author: hhr
 * @description:
 * @create: 2020-01-05 16:00
 **/
public class AB {

    private String a;
    private int b;

    public AB() {
    }

    public AB(String a, int b) {
        this.a = a;
        this.b = b;
    }

    public String getA() {
        return a;
    }

    public void setA(String a) {
        this.a = a;
    }

    public int getB() {
        return b;
    }

    public void setB(int b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "AB{" +
                "a='" + a + '\'' +
                ", b=" + b +
                '}';
    }
}
