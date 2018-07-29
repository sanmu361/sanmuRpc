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

    private MethodAccess methodAccess;
    private String lastMethodName = "";
    private int lastMethodIndex;


    public RpcServerRequestHandlerRunnable(Class<?> interfaceClass, Object serviceProvider, RpcInvokeHook rpcInvokeHook, BlockingQueue<RpcRequestWrapper> requestQueue) {
        this.interfaceClass = interfaceClass;
        this.serviceProvider = serviceProvider;
        this.rpcInvokeHook = rpcInvokeHook;
        this.requestQueue = requestQueue;

        methodAccess = MethodAccess.get(interfaceClass);
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
                if(!methodName.equals(lastMethodName))
                {
                    lastMethodIndex = methodAccess.getIndex(methodName);
                    lastMethodName = methodName;
                }

                result = methodAccess.invoke(serviceProvider, lastMethodIndex, args);

                Channel channel = rpcRequestWrapper.getChannel();
                Response rpcResponse = new Response();
                rpcResponse.setId(rpcRequestWrapper.getId());
                rpcResponse.setResult(result);
                rpcResponse.setInvokeSuccess(true);
                channel.writeAndFlush(rpcResponse);

                if(rpcInvokeHook != null)
                    rpcInvokeHook.afterInvoke(methodName, args);




            } catch (Exception e) {
                Channel channel = rpcRequestWrapper.getChannel();
                Response rpcResponse = new Response();
                rpcResponse.setId(rpcRequestWrapper.getId());
                rpcResponse.setThrowable(e);
                rpcResponse.setInvokeSuccess(false);
                channel.writeAndFlush(rpcResponse);

            }

        }
    }
}
