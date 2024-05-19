package com.company;

public class Test {
    public static void main(String[] args) {
        Integer x = 1;
        System.out.println(x);
        m(x);
        System.out.println(x);
    }

    private static void m(Integer x) {
        x = new Integer(8);
    }
}
