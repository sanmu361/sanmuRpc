package com.sanmu.sanmuRpc.context;

import java.io.Serializable;

public class Request implements Serializable{

    private int id;
    private String methodName;
    private Object[] args;

    public Request(int id ,String methodName, Object [] args){
        this.id = id;
        this .methodName = methodName;
        this.args = args;
    }

    public int getId() {
        return id;
    }

    public String getMethodName() {
        return methodName;
    }

    public Object[] getArgs() {
        return args;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public void setArgs(Object[] args) {
        this.args = args;
    }
}
