package com.sanmu.sanmuRpc;

public class TestInterfaceImpl implements TestInterface {
    @Override
    public String testMethod01() {
        System.out.println("hello");
        return "world!";
    }
}
