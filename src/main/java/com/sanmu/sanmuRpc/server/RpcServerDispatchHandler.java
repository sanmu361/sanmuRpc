package com.sanmu.sanmuRpc.server;

import com.sanmu.sanmuRpc.context.Request;
import com.sanmu.sanmuRpc.context.RpcRequestWrapper;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class RpcServerDispatchHandler extends ChannelInboundHandlerAdapter {
    private RpcServerRequestHandler rpcServerRequestHandler;

    public RpcServerDispatchHandler(
            RpcServerRequestHandler rpcServerRequestHandler)
    {
        this.rpcServerRequestHandler = rpcServerRequestHandler;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg)
            throws Exception
    {
        Request request = (Request)msg;
        RpcRequestWrapper rpcRequestWrapper = new RpcRequestWrapper(request, ctx.channel());

        rpcServerRequestHandler.addRequest(rpcRequestWrapper);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)
            throws Exception
    {

    }

}
