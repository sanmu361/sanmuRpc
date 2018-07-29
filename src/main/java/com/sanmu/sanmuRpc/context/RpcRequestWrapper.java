package com.sanmu.sanmuRpc.context;

import io.netty.channel.Channel;

public class RpcRequestWrapper {
    private final Request request;
    private final Channel channel;

    public RpcRequestWrapper(Request request, Channel channel)
    {
        this.request = request;
        this.channel = channel;
    }

    public int getId()
    {
        return request.getId();
    }

    public String getMethodName()
    {
        return request.getMethodName();
    }

    public Object[] getArgs()
    {
        return request.getArgs();
    }

    public Channel getChannel()
    {
        return channel;
    }
}
