package com.sanmu.sanmuRpc.serializer.netty1;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sanmu.sanmuRpc.context.Request;

public class RequestSerializer extends Serializer<Request> {
    @Override
    public void write(Kryo kryo, Output output, Request request)
    {
        output.writeInt(request.getId());
        output.writeByte(request.getMethodName().length());
        output.write(request.getMethodName().getBytes());
        kryo.writeClassAndObject(output, request.getArgs());
    }

    @Override
    public Request read(Kryo kryo, Input input, Class<Request> type)
    {
        Request request;
        int id = input.readInt();
        byte methodLength = input.readByte();
        byte[] methodBytes = input.readBytes(methodLength);
        String methodName = new String(methodBytes);
        Object[] args = (Object[])kryo.readClassAndObject(input);

        request = new Request(id, methodName, args);

        return request;
    }
}
