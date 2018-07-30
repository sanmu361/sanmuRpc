package com.sanmu.sanmuRpc.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.ByteBufOutputStream;
import org.objenesis.strategy.StdInstantiatorStrategy;

import java.io.IOException;


/**
 * ${DESCRIPTION}
 *
 * @author yansen
 * @create 2018-07-17 14:19
 **/
public class KryoUtils {

    private static final byte[] LENGTH_PLACEHOLDER = new byte[4];
    private static final ThreadLocal<Kryo> local = new ThreadLocal<Kryo>(){
        protected Kryo initialValue(){
            Kryo kryo = new Kryo();

            kryo.setReferences(true);

            ((Kryo.DefaultInstantiatorStrategy)kryo.getInstantiatorStrategy()).setFallbackInstantiatorStrategy(new StdInstantiatorStrategy());
            return kryo;
        }
    };

    public static Kryo getInstance(){
        return local.get();
    }


    public static <T> void serialize(T obj, ByteBuf byteBuf){

        int startIdx = byteBuf.writerIndex();

        ByteBufOutputStream byteArrayOutputStream = new ByteBufOutputStream(byteBuf);

        try {
            byteArrayOutputStream.write(LENGTH_PLACEHOLDER);
        } catch (IOException e) {
            e.printStackTrace();
        }

        Output output = new Output(1024*4, -1);
        output.setOutputStream(byteArrayOutputStream);

        Kryo kryo = getInstance();

        kryo.writeClassAndObject(output,obj);

        output.flush();
        output.close();

        int endIndex = byteBuf.writerIndex();

        byteBuf.setInt(startIdx,endIndex - startIdx - 4);
    }

    public static <T> T deserialize(ByteBuf byteBuf){
        try{
            if(byteBuf == null){
                return null;
            }
            Input input = new Input(new ByteBufInputStream(byteBuf));
            Kryo kryo = getInstance();
            return (T)kryo.readClassAndObject(input);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args) {

    }

}
