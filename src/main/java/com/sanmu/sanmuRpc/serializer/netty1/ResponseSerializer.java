package com.sanmu.sanmuRpc.serializer.netty1;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.Serializer;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.sanmu.sanmuRpc.context.Response;

public class ResponseSerializer extends Serializer<Response> {
    @Override
    public void write(Kryo kryo, Output output, Response response) {
        output.writeInt(response.getId());
        output.writeBoolean(response.isInvokeSuccess());
        if(response.isInvokeSuccess())
            kryo.writeClassAndObject(output, response.getResult());
        else
            kryo.writeClassAndObject(output, response.getThrowable());
    }

    @Override
    public Response read(Kryo kryo, Input input, Class<Response> type)
    {
        int id = input.readInt();
        boolean isInvokeSuccess = input.readBoolean();
        Object resultOrThrowable = kryo.readClassAndObject(input);

        Response response = new Response(id, resultOrThrowable, isInvokeSuccess);

        return response;
    }
}
