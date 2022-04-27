package com.tang.netty.c4;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
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
public class Server {
    public static void main(String[] args) throws IOException {
        // 使用 nio 来理解阻塞模式 单线程

        // 0、ByteBuffer
        ByteBuffer buffer = ByteBuffer.allocate(16);

        // 1、创建服务器
        ServerSocketChannel ssc = ServerSocketChannel.open();

        // 2、绑定监听窗口
        ssc.bind(new InetSocketAddress(8080));

        //3、连接集合
        List<SocketChannel> channels = new ArrayList<>();
        while (true) {
            //4、accept 建立与客户端的连接，SocketChannel 用来与客户端之间通信
            log.debug("connecting...");
            //阻塞方法，线程停止运行
            SocketChannel sc = ssc.accept();
            log.debug("connected...{}",sc);
            channels.add(sc);

            for (SocketChannel channel : channels) {
                // 5、接收客户端发送的数据
                log.debug("before read...{}",channel);
                //阻塞方法，线程停止运行
                channel.read(buffer);
                buffer.flip();
                debugRead(buffer);
                buffer.clear();
                log.debug("after read...{}",channel);
            }
        }


    }
}