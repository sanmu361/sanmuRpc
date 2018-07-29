//package com.sanmu.sanmuRpc.proxy;
//
//import org.mockito.cglib.proxy.Enhancer;
//
///**
// * ${DESCRIPTION}
// *
// * @author yansen
// * @create 2018-04-11 12:27
// **/
//public class CglibTest {
//
//    public static void main(String[] args) {
//
//        Enhancer enhancer = new Enhancer();
//
//        enhancer.setSuperclass(Student.class);
//        enhancer.setCallback(new MyProxy());
//
//        Student student = (Student)enhancer.create();
//
//        student.sayHell("yansen ");
//
//    }
//}
