package com.sanmu.sanmuRpc.context;

public class Response {
    private int id;
    private Object result;
    private Throwable throwable;
    private boolean isInvokeSuccess;

    public int getId() {
        return id;
    }

    public Object getResult() {
        return result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public boolean isInvokeSuccess() {
        return isInvokeSuccess;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setInvokeSuccess(boolean invokeSuccess) {
        isInvokeSuccess = invokeSuccess;
    }
}
