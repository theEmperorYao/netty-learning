package com.tang.netty.nio.c1;

import java.nio.ByteBuffer;

import static com.tang.netty.nio.c1.ByteBufferUtil.debugAll;

/**
 * @Classname TestBufferRead
 * @Description TODO
 * @Date 2022/4/5 03:49
 * @Author by tangyao
 */
public class TestBufferRead {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        buffer.put(new byte[]{'a', 'b', 'c', 'd'});

        buffer.flip();

        //从头开始读
      /*  buffer.get(new byte[4]);
        debugAll(buffer);
        buffer.rewind();
        System.out.println((char) buffer.get());
        debugAll(buffer);*/

        //mark & reset
        //mark 做一个标记，记录position位置，reset 是将 position 重置到mark位置

        /*System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());
        //加标记，索引2的位置
        buffer.mark();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());

        buffer.reset();
        System.out.println((char) buffer.get());
        System.out.println((char) buffer.get());*/
        //get(i) 不会改变读索引的位置
        System.out.println((char) buffer.get(3));
        debugAll(buffer);

    }
}
