package com.sanmu.sanmuRpc.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * ${DESCRIPTION}
 *
 * @author yansen
 * @create 2017-07-31 14:15
 **/
public class MyInvokationHandler implements InvocationHandler {
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("----正在执行该方法是"+ method.getName());
        if(args != null){
            System.out.println("----传入的实参：");
            for(Object val : args){
                System.out.println(val);
            }
        }else{
            System.out.println("调用该方法没有实参！");
        }
        return null;
    }
}
