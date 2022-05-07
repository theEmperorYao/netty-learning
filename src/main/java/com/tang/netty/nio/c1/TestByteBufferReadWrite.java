package com.tang.netty.nio.c1;

import java.nio.ByteBuffer;

import static com.tang.netty.nio.c1.ByteBufferUtil.debugAll;

/**
 * @Classname TestByteBufferReadWrite
 * @Description TODO
 * @Date 2022/4/5 03:24
 * @Author by tangyao
 */
public class TestByteBufferReadWrite {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        //'a'
        buffer.put((byte) 97);
        debugAll(buffer);
        buffer.put(new byte[]{0x62, 0x63, 0x64});
        debugAll(buffer);

//        System.out.println("buffer.get() = " + buffer.get());

        buffer.flip();
        System.out.println("buffer.get() = " + buffer.get());
        debugAll(buffer);
        buffer.compact();
        debugAll(buffer);

        buffer.put(new byte[]{0x65, 0x66});
        debugAll(buffer);

    }
}
