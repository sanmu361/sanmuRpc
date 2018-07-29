package com.sanmu.sanmuRpc.future;

import com.sanmu.sanmuRpc.exception.RpcTimeoutException;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class RpcFuture {

    public final static int STATE_AWAIT = 0;
    public final static int STATE_SUCCESS = 1;
    public final static int STATE_EXCEPTION = 2;

    private CountDownLatch countDownLatch;
    private Object result;
    private Throwable throwable;
    private int state;
    private RpcFutureListener rpcFutureListener = null;


    public RpcFuture(){
        countDownLatch = new CountDownLatch(1);

        state = STATE_AWAIT;
    }

    public Object get() throws Throwable {
        try {
            countDownLatch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        if(state == STATE_SUCCESS){
            return result;
        } else if (state == STATE_EXCEPTION) {
            throw  throwable;
        }else{
            throw  new RuntimeException("RpcFuture Exception!");
        }
    }

    public void setCountDownLatch(CountDownLatch countDownLatch) {
        this.countDownLatch = countDownLatch;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public void setThrowable(Throwable throwable) {
        this.throwable = throwable;
    }

    public void setState(int state) {
        this.state = state;
    }

    public void setRpcFutureListener(RpcFutureListener rpcFutureListener) {
        this.rpcFutureListener = rpcFutureListener;
    }

    public CountDownLatch getCountDownLatch() {
        return countDownLatch;
    }

    public Object getResult() {
        return result;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public int getState() {
        return state;
    }

    public RpcFutureListener getRpcFutureListener() {
        return rpcFutureListener;
    }

    public Object get(long timeout) throws Throwable {
        boolean  awaitSuccess = true;

        try {
            awaitSuccess  = countDownLatch.await(timeout, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if(!awaitSuccess){
            throw new RpcTimeoutException();
        }

        if(state == STATE_SUCCESS){
            return result;
        }else if(state == STATE_EXCEPTION){
            throw throwable;
        }
        else{
            throw new RuntimeException("RpcFutue Exception!");
        }




    }





}
