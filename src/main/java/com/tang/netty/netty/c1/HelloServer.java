package com.tang.netty.netty.c1;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;

/**
 * @Classname HelloServer
 * @Description TODO
 * @Date 2022/5/7 07:51
 * @Author by tangyao
 */
public class HelloServer {

    public static void main(String[] args) {
        //1、启动器，负责组装netty组件，启动服务器
        new ServerBootstrap()
                //2、BossEventLoop,WorkerEventLoop(selector,thread),group 组
                .group(new NioEventLoopGroup())
                //3、选择服务器的ServerSocketChannel 实现
                .channel(NioServerSocketChannel.class)
                //4、boss负责处理连接  worker（child）负责读写，决定了worker（child）能执行哪些操作（handler）
                .childHandler(new ChannelInitializer<NioServerSocketChannel>() {
                    @Override
                    protected void initChannel(NioServerSocketChannel ch) throws Exception {
                        //6、添加具体 handler
                        // 将ByteBuf 转换为字符串
                        ch.pipeline().addLast(new StringDecoder());
                        // 自定义handler
                        ch.pipeline().addLast(new ChannelInboundHandlerAdapter() {
                            // 读事件
                            @Override
                            public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
                                // 打印上一步转换好的字符串
                                System.out.println(msg);
                            }
                        });
                    }
                })
                //7、绑定监听端口
                .bind(8888);


    }
}