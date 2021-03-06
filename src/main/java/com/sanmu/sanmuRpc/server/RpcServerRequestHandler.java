package com.sanmu.sanmuRpc.server;

import com.sanmu.sanmuRpc.aop.RpcInvokeHook;
import com.sanmu.sanmuRpc.context.Request;
import com.sanmu.sanmuRpc.context.RpcRequestWrapper;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingDeque;

public class RpcServerRequestHandler {

    private Class<?> interfaceClass;
    private Object serviceProvider;
    private RpcInvokeHook rpcInvokeHook;

    private int threads;
    private ExecutorService threadPool;
    private BlockingQueue<RpcRequestWrapper> requestQueue = new LinkedBlockingDeque<RpcRequestWrapper>();

    public RpcServerRequestHandler(Class<?> interfaceClass, Object serviceProvider, int threads,RpcInvokeHook rpcInvokeHook) {
        this.interfaceClass = interfaceClass;
        this.serviceProvider = serviceProvider;
        this.threads = threads;
        this.rpcInvokeHook = rpcInvokeHook;
    }

    public void start(){
        threadPool = Executors.newFixedThreadPool(threads);

        for(int i = 0; i < threads; i++){
            threadPool.execute(new RpcServerRequestHandlerRunnable(interfaceClass,serviceProvider,rpcInvokeHook,requestQueue));
        }
    }

    public void addRequest(RpcRequestWrapper rpcRequestWrapper){
        try {
            requestQueue.put(rpcRequestWrapper);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
