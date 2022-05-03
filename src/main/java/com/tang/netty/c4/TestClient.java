package com.tang.netty.c4;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;

/**
 * @Classname TestClient
 * @Description TODO
 * @Date 2022/5/3 16:15
 * @Author by tangyao
 */
public class TestClient {
    public static void main(String[] args) throws IOException {
        SocketChannel sc = SocketChannel.open();
        sc.connect(new InetSocketAddress("localhost", 8080));
        sc.write(Charset.defaultCharset().encode("123456789abcdef"));
        System.in.read();

    }
}
