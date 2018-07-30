package com.sanmu.sanmuRpc.server;

import com.esotericsoftware.reflectasm.MethodAccess;
import com.sanmu.sanmuRpc.aop.RpcInvokeHook;
import com.sanmu.sanmuRpc.context.Request;
import com.sanmu.sanmuRpc.context.Response;
import com.sanmu.sanmuRpc.context.RpcRequestWrapper;
import io.netty.channel.Channel;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.concurrent.BlockingQueue;

public class RpcServerRequestHandlerRunnable implements Runnable {

    private Class<?> interfaceClass;
    private Object serviceProvider;
    private RpcInvokeHook rpcInvokeHook;
    private BlockingQueue<RpcRequestWrapper> requestQueue;

    private RpcRequestWrapper rpcRequestWrapper;

    private String lastMethodName = "";

    private int lastMethodIndex;


    public RpcServerRequestHandlerRunnable(Class<?> interfaceClass, Object serviceProvider, RpcInvokeHook rpcInvokeHook, BlockingQueue<RpcRequestWrapper> requestQueue) {
        this.interfaceClass = interfaceClass;
        this.serviceProvider = serviceProvider;
        this.rpcInvokeHook = rpcInvokeHook;
        this.requestQueue = requestQueue;

//        methodAccess = MethodAccess.get(interfaceClass);
    }

    @Override
    public void run() {
        while(true){

            try{
                rpcRequestWrapper = requestQueue.take();

                String methodName =rpcRequestWrapper.getMethodName();
                Object[] args = rpcRequestWrapper.getArgs();

                if(rpcInvokeHook != null)
                    rpcInvokeHook.beforeInvoke(methodName, args);

                Object result = null;
//                if(!methodName.equals(lastMethodName))
//                {
//                    lastMethodIndex = methodAccess.getIndex(methodName);
//                    lastMethodName = methodName;
//                }

                Class[] argTypes = new Class[0];

                if(args != null){
                    argTypes = new Class[args.length];
                    for(int i = 0; i < args.length; i++){
                        argTypes[i] = args[i].getClass();
                    }
                }


                Method method = serviceProvider.getClass().getMethod(methodName,argTypes);

                result = method.invoke(serviceProvider, args);

                Channel channel = rpcRequestWrapper.getChannel();
                int id = rpcRequestWrapper.getId();
                Response rpcResponse =new Response(id, result, true);
                channel.writeAndFlush(rpcResponse);

                if(rpcInvokeHook != null)
                    rpcInvokeHook.afterInvoke(methodName, args);

            } catch (Exception e) {
                e.printStackTrace();
                Channel channel = rpcRequestWrapper.getChannel();
                int id = rpcRequestWrapper.getId();
                Response rpcResponse = new Response(id, e, false);
                channel.writeAndFlush(rpcResponse);

            }

        }
    }
}
