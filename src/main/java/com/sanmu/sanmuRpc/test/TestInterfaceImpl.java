package com.sanmu.sanmuRpc.test;

public class TestInterfaceImpl implements TestInterface {
    @Override
    public String testMethod01() {
        System.out.println("hello");
        return "world!";
    }
}
