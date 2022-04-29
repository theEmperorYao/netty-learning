package com.tang.netty.c4;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Title: Client
 * @Description:
 * @author: tangyao
 * @date: 2022/4/27 15:36
 * @Version: 1.0
 */

public class Client2 {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(Charset.defaultCharset().encode("hello"));
        System.out.println("waiting...");
        sc.close();
    }
}