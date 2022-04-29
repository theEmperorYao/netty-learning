package com.tang.netty.c4;


import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.util.Iterator;
import java.util.Set;

import static com.tang.netty.c1.ByteBufferUtil.debugAll;

/**
 * @Title: Server
 * @Description:
 * @author: tangyao
 * @date: 2022/4/27 15:25
 * @Version: 1.0
 */
@Slf4j
public class Server5 {

    /**
     * （1)Socket 和ServerSocket 是一对 他们是java.net下面实现socket通信的类
     * (2)SocketChannel 和ServerSocketChannel是一对 他们是java.nio下面实现通信的类 支持异步通信
     * (3)服务器必须先建立ServerSocket或者ServerSocketChannel 来等待客户端的连接
     * (4)客户端必须建立相对应的Socket或者SocketChannel来与服务器建立连接
     * (5)服务器接受到客户端的连接受，再生成一个Socket或者SocketChannel与此客户端通信
     *
     * @param args
     * @throws IOException
     */

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
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            log.debug("selectedKeys length:{}", selectionKeys.size());
            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey key = iterator.next();
                // 处理key的时候，要从selectedKeys 集合中删除，否则下次处理就会有问题
                iterator.remove();
                log.debug("key:{}", key);
                //5.区分事件类型

                if (key.isAcceptable()) {
                    ServerSocketChannel channel = (ServerSocketChannel) key.channel();
                    SocketChannel sc = channel.accept();
                    sc.configureBlocking(false);
                    SelectionKey scKey = sc.register(selector, 0, null);
                    scKey.interestOps(SelectionKey.OP_READ);

                    log.debug("{}", sc);
                } else if (key.isReadable()) {
                    //拿到触发事件的channel
                    try {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer buffer = ByteBuffer.allocate(4);
                        // 如果是正常断开，read的方法返回值是-1
                        int read = channel.read(buffer);
                        if (read == -1) {
                            key.cancel();
                        } else {
                            split(buffer);
                        }

                    } catch (IOException e) {
                        e.printStackTrace();
                        // 因为客户端断开了，因此需要将key取消，（将selector的keys 集合中真正的删除key）
                        key.cancel();
                    }

                }

            }


        }


    }

    private static void split(ByteBuffer source) {
        source.flip();
        for (int i = 0; i < source.limit(); i++) {
            // 找到一条完整的消息
            if (source.get(i) == '\n') {
                int length = i + 1 - source.position();
                // 把这条完整的消息存入新的ByteBuffer
                ByteBuffer target = ByteBuffer.allocate(length);
                //从source读，向target写
                for (int j = 0; j < length; j++) {
                    target.put(source.get());
                }
                debugAll(target);
            }
        }
        source.compact();
    }
}