package com.sanmu.sanmuRpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Proxy;

/**
 * ${DESCRIPTION}
 *
 * @author yansen
 * @create 2017-07-31 14:18
 **/
public class ProxyTest {

    public static void main(String[] args){
        InvocationHandler handler = new MyInvokationHandler();




//或更简单
//        Person f = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(),
//                new Class[] { Person.class },
//                handler);
//
//
//        f.walk();

        Person p = (Person) Proxy.newProxyInstance(Person.class.getClassLoader(),
                new Class[]{Person.class}
                ,handler);

        p.walk();

//        p.sayHell("Test");


//        p = (Person)Proxy.newProxyInstance(Student.class.getClassLoader(),new Class[]{Person.class},handler);
//
//        p.walk();
//
//        p.sayHell("yansen");
    }
}
