package com.sanmu.sanmuRpc.exception;

public class RpcTimeoutException extends RuntimeException {

    private static final long erialVersionUID =  -3399060930740626516L;

    public RpcTimeoutException(){
        super("time out when clalling a Rpc Invoke!");
    }
}
