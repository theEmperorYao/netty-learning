package com.tang.netty.nio.c4;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.*;
import java.util.Iterator;

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

        // accept -会在有链接请求时触发
        // connect -是客户端，连接建立后触发
        // read -可读事件
        // write -可写事件

        //1、创建selector , 管理多个channel
        Selector selector = Selector.open();

        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);

        //2、建立selector 和 channel 的联系（注册）

        // SelectionKey 就是将来事件发生后，通过它可以知道事件和哪个channel的事件
        SelectionKey sscKey = ssc.register(selector, 0, null);
        sscKey.interestOps(SelectionKey.OP_ACCEPT);
        log.debug("register key:{}", sscKey);
        ssc.bind(new InetSocketAddress(8080));
        while (true) {
            // 3、select 方法,没有事件发生，线程阻塞，有事件。线程才会回复运行
            // select 在事件未处理时，他不会阻塞
            selector.select();

            //4、处理事件,selectedKeys 内部包含了所有发生的事件
            Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                log.debug("key:{}", key);
//                ServerSocketChannel channel = (ServerSocketChannel) key.channel();
//                SocketChannel sc = channel.accept();
//                log.debug("{}", sc);
                key.cancel();
            }


        }


    }
}