package com.sanmu.sanmuRpc.client;

import com.sanmu.sanmuRpc.aop.RpcInvokeHook;

import java.lang.reflect.Proxy;

public class RpcClientProxyBuilder {

    public static class ProxyBuilder<T>{
        private Class<T> clazz;
        private RpcClient rpcClient;

        private long timeoutMills = 0;
        private RpcInvokeHook rpcInvokeHook = null;
        private String host;
        private int port;
        private int threads;

        private ProxyBuilder(Class<T> clazz){
            this.clazz = clazz;
        }

        public ProxyBuilder<T> timeout(long timeoutMills){
            this.timeoutMills = timeoutMills;
            if(timeoutMills < 0){
                throw  new IllegalArgumentException("timeoutMills can not be minus!");
            }

            return this;
        }

        public ProxyBuilder<T> hook(RpcInvokeHook hook){
            this.rpcInvokeHook = hook;
            return this;
        }

        public ProxyBuilder<T> connect(String host,int port){
            this.host = host;
            this.port = port;
            return this;
        }

        public ProxyBuilder<T> threads(int threadCount)
        {
            this.threads = threadCount;
            return this;
        }

        public T build(){
            rpcClient = new RpcClient(timeoutMills, rpcInvokeHook, host, port, threads);
            rpcClient.connect();

            return(T) Proxy.newProxyInstance(clazz.getClassLoader(),new Class<?>[]{clazz},rpcClient);
        }
        public RpcClientAsyncProxy buildAsyncProxy(){
            rpcClient = new RpcClient(timeoutMills, rpcInvokeHook, host, port, threads);
            rpcClient.connect();

            return new RpcClientAsyncProxy(rpcClient);
        }
    }

    public static <T> ProxyBuilder<T> create(Class<T> targetClass){
        return new ProxyBuilder<T>(targetClass);
    }


}
