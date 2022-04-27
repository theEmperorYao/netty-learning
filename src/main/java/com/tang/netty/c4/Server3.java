package com.tang.netty.c4;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
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
public class Server3 {
    public static void main(String[] args) throws IOException {

        //1、创建selector , 管理多个channel
        Selector selector = Selector.open();

        ByteBuffer buffer = ByteBuffer.allocate(16);
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2、建立selector 和 channel 的联系（注册）

        // SelectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);

        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            // 3、select 方法
        }


    }
}