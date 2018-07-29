package com.sanmu.sanmuRpc.client;

import com.sanmu.sanmuRpc.context.Response;
import com.sanmu.sanmuRpc.future.RpcFuture;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentMap;

public class RpcClientResponseHandleRunnable implements Runnable {
    private ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap;
    private BlockingQueue<Response> responseQueue;

    public RpcClientResponseHandleRunnable(
            ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap,
            BlockingQueue<Response> responseQueue)
    {
        this.invokeIdRpcFutureMap = invokeIdRpcFutureMap;
        this.responseQueue = responseQueue;
    }

    public void run()
    {
        while(true)
        {
            try
            {
                Response rpcResponse = responseQueue.take();

                int id = rpcResponse.getId();
                RpcFuture rpcFuture = invokeIdRpcFutureMap.remove(id);

                if(rpcResponse.isInvokeSuccess())
                    rpcFuture.setResult(rpcResponse.getResult());
                else
                    rpcFuture.setThrowable(rpcResponse.getThrowable());
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
        }
    }
}
