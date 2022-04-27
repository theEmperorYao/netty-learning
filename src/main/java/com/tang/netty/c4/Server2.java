package com.tang.netty.c4;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.ArrayList;
import java.util.List;

import static com.tang.netty.c1.ByteBufferUtil.debugRead;

/**
 * @Title: Server
 * @Description:
 * @author: tangyao
 * @date: 2022/4/27 15:25
 * @Version: 1.0
 */
@Slf4j
public class Server2 {
    public static void main(String[] args) throws IOException {
        // 使用 nio 来理解阻塞模式 单线程

        // 0、ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1、创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();
        // 非阻塞模式
        ssc.configureBlocking(false);

        // 2、绑定监听窗口
        ssc.bind(new InetSocketAddress(8080));

        //3、连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            //4、accept 建立与客户端的连接，SocketChannel 用来与客户端之间通信

            //非阻塞，线程还会继续运行，如果没有建立连接，但sc是null
            SocketChannel sc = ssc.accept();
            if (sc != null) {
                log.debug("connected...{}", sc);
                channels.add(sc);
            }

            for (SocketChannel channel : channels) {
                // 5、接收客户端发送的数据

                channel.configureBlocking(false);
                //非阻塞，线程仍然会继续运行，如果没有读到数据，read返回0
                int read = channel.read(buffer);
                if (read > 0) {
                    buffer.flip();
                    debugRead(buffer);
                    buffer.clear();
                    log.debug("after read...{}", channel);
                }

            }

        }


    }
}