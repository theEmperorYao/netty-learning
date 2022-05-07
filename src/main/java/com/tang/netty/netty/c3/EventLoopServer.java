package com.tang.netty.netty.c3;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.extern.slf4j.Slf4j;

import java.nio.charset.Charset;

/**
 * @Classname EventLoopServer
 * @Description TODO
 * @Date 2022/5/7 22:45
 * @Author by tangyao
 */
@Slf4j
public class EventLoopServer {
    public static void main(String[] args) {

        // 细分2：创建一个独立的 EventLoopGroup
        EventLoopGroup group = new DefaultEventLoopGroup();


        new ServerBootstrap()
                // boss 和 worker
                //细分1、boss 只负责NioServerSocketChannel accept事件 ，  worker 只负责 socketChannel 上的读写
                .group(new NioEventLoopGroup(), new NioEventLoopGroup(2))
                .channel(NioServerSocketChannel.class)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ch.pipeline().addLast("handler1", new ChannelInboundHandlerAdapter() {

                            // ByteBuf
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.debug(buf.toString(Charset.defaultCharset()));
                                //将消息传递给下一个handler
                                ctx.fireChannelRead(msg);
                            }
                        }).addLast(group, "handler2", new ChannelInboundHandlerAdapter() {

                            // ByteBuf
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                ByteBuf buf = (ByteBuf) msg;
                                log.debug(buf.toString(Charset.defaultCharset()));
                            }
                        });
                    }
                })
                .bind(8080);
    }
}
