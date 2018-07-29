package com.sanmu.sanmuRpc.aop;

public interface RpcInvokeHook {
    public void beforeInvoke(String methodName,Object[] args);
    public void afterInvoke(String methodName,Object[] args);
}
