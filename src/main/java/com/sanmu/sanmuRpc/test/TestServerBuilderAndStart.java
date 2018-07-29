package com.sanmu.sanmuRpc.test;

import com.sanmu.sanmuRpc.aop.RpcInvokeHook;
import com.sanmu.sanmuRpc.server.RpcServer;
import com.sanmu.sanmuRpc.server.RpcServerBuilder;

public class TestServerBuilderAndStart {
    public static void main(String[] args) {
        TestInterface testInterface = new TestInterface()
        {
            public String testMethod01()
            {
                return "return from server";
            }
        };

        RpcInvokeHook hook = new RpcInvokeHook()
        {
            public void beforeInvoke(String methodName, Object[] args)
            {
                System.out.println("beforeInvoke in server" + methodName);
            }

            public void afterInvoke(String methodName, Object[] args)
            {
                System.out.println("afterInvoke in server" + methodName);
            }
        };

        RpcServer rpcServer = RpcServerBuilder.create()
                .serviceInterface(TestInterface.class)
                .serviceProvider(testInterface)
                .threads(4)
                .hook(hook)
                .bind(8765)
                .build();
        rpcServer.start();

    }
}
