package com.sanmu.sanmuRpc.serializer.netty2;

//import com.sanmu.sanmuRpc.serializer.netty1.KryoSerializer;
import com.sanmu.sanmuRpc.serializer.KryoUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class NettyKryoEncoder extends MessageToByteEncoder<Object> {
    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out)
            throws Exception
    {
        KryoUtils.serialize(msg, out);
    }
}
