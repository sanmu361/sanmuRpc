package com.sanmu.sanmuRpc.client;

import com.sanmu.sanmuRpc.context.Response;
import com.sanmu.sanmuRpc.future.RpcFuture;

import java.util.concurrent.*;

public class RpcClientResponseHandler {
    private ConcurrentMap<Integer, RpcFuture> invokeIdRpcFutureMap = new ConcurrentHashMap<Integer, RpcFuture>();

    private ExecutorService threadPool;
    private BlockingQueue<Response> responseQueue = new LinkedBlockingQueue<Response>();

    public RpcClientResponseHandler(int threads)
    {
        threadPool = Executors.newFixedThreadPool(threads);
        for(int i=0; i<threads; i++)
        {
            threadPool.execute(new RpcClientResponseHandleRunnable(invokeIdRpcFutureMap, responseQueue));
        }
    }

    public void register(int id, RpcFuture rpcFuture)
    {
        invokeIdRpcFutureMap.put(id, rpcFuture);
    }

    public void addResponse(Response rpcResponse)
    {
        responseQueue.add(rpcResponse);
    }
}
