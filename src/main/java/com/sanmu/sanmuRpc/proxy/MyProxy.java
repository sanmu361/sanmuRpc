//package com.sanmu.sanmuRpc.proxy;
//
//
//import java.lang.reflect.Method;
//
///**
// * ${DESCRIPTION}
// *
// * @author yansen
// * @create 2018-04-11 12:22
// **/
//public class MyProxy implements MethodInterceptor {
//    @Override
//    public Object intercept(Object o, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
//        System.out.println("----正在执行该方法是传入的实参：");
//
//        if(args != null){
//            for(Object val : args){
//                System.out.println(val);
//            }
//        }else{
//            System.out.println("调用该方法没有实参！");
//        }
//
//        methodProxy.invokeSuper(o,args);
//        return null;
//    }
//}
