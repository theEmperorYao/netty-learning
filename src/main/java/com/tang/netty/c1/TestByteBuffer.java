package com.tang.netty.c1;

import lombok.extern.slf4j.Slf4j;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Classname Test
 * @Description TODO
 * @Date 2022/4/4 22:42
 * @Author by tangyao
 */
@Slf4j
public class TestByteBuffer {
    public static void main(String[] args) {

        //FileChannel
        //1、输入输出流
        //2、RandomAccessFile
        try (FileChannel channel = new FileInputStream("src/main/resources/data.txt").getChannel()) {
            // 准备缓存区
            ByteBuffer buffer = ByteBuffer.allocate(10);
            //从channel读取数据，向buffer写入

            while (true) {
                int len = channel.read(buffer);
                log.debug("读取到的字节数{}", len);
                if (len == -1) {
                    break;
                }
                //打印buffer的内容
                //切换读模式
                buffer.flip();
 
                while (buffer.hasRemaining()) {
                    //是否还有剩余的数据
                    byte b = buffer.get();
                    log.debug("读取到的字节{}", (char) b);
                }
                // 切换为写模式
                buffer.clear();

            }


        } catch (IOException e) {
        }


    }
}
