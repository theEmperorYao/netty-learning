package com.tang.netty.c1;

import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

import static com.tang.netty.c1.ByteBufferUtil.debugAll;

/**
 * @Classname TestByteBufferString
 * @Description TODO
 * @Date 2022/4/6 07:58
 * @Author by tangyao
 */
public class TestByteBufferString {
    public static void main(String[] args) {
        //1、字符串转为ByteBuffer 处于写模式
        ByteBuffer buffer1 = ByteBuffer.allocate(16);
        buffer1.put("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer1);

        //2、Charset 自动切换为读模式
        ByteBuffer buffer2 = StandardCharsets.UTF_8.encode("hello");
        debugAll(buffer2);

        //3、wrap
        ByteBuffer buffer3 = ByteBuffer.wrap("hello".getBytes(StandardCharsets.UTF_8));
        debugAll(buffer3);

        String s = StandardCharsets.UTF_8.decode(buffer2).toString();
        System.out.println(s);

        // 先手动切换为读模式
        buffer1.flip();
        String s1 = StandardCharsets.UTF_8.decode(buffer1).toString();
        System.out.println(s1);



    }
}
