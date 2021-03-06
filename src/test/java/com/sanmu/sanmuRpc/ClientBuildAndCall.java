package com.sanmu.sanmuRpc;

import com.sanmu.sanmuRpc.aop.RpcInvokeHook;
import com.sanmu.sanmuRpc.client.RpcClientProxyBuilder;

public class ClientBuildAndCall {

    public static void main(String[] args) {
        RpcInvokeHook hook = new RpcInvokeHook()
        {
            public void beforeInvoke(String method, Object[] args)
            {
                System.out.println("before invoke in client" + method);
            }

            public void afterInvoke(String method, Object[] args)
            {
                System.out.println("after invoke in client" + method);
            }
        };

        TestInterface testInterface
                = RpcClientProxyBuilder.create(TestInterface.class)
                .timeout(0)
                .threads(4)
                .hook(hook)
                .connect("127.0.0.1", 8765)
                .build();

        for(int i=0; i<10; i++) {
           System.out.println("result = " + testInterface.testMethod01() );
        }

    }
}
