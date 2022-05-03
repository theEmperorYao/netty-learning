package com.tang.netty.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.atomic.AtomicInteger;

import static com.tang.netty.c1.ByteBufferUtil.debugAll;

/**
 * @Classname MultiThreadServer
 * @Description TODO
 * @Date 2022/5/3 14:08
 * @Author by tangyao
 */
@Slf4j
public class MultiThreadServer3 {

    public static void main(String[] args) throws IOException {
        Thread.currentThread().setName("boss");
        ServerSocketChannel ssc = ServerSocketChannel.open();
        ssc.configureBlocking(false);
        Selector boss = Selector.open();
        SelectionKey bossKey = ssc.register(boss, 0, null);
        bossKey.interestOps(SelectionKey.OP_ACCEPT);
        ssc.bind(new InetSocketAddress(8080));
        //1、创建固定数量的worker

        Worker[] workers = new Worker[2];
        for (int i = 0; i < workers.length; i++) {
            workers[i] = new Worker("worker-" + i);
        }

        AtomicInteger index = new AtomicInteger();

        while (true) {
            boss.select();
            Iterator<SelectionKey> iter = boss.selectedKeys().iterator();
            while (iter.hasNext()) {
                SelectionKey key = iter.next();
                iter.remove();
                if (key.isAcceptable()) {
                    SocketChannel sc = ssc.accept();
                    sc.configureBlocking(false);
                    log.debug("connected...{}", sc.getRemoteAddress());
                    //2、关联selector
                    log.debug("before register...{}", sc.getRemoteAddress());
                    //boss 调用 初始化selector，启动worker-0
                    // round robin 轮训
                    workers[index.getAndIncrement() % workers.length].register(sc);
                    log.debug("after register  ...{}", sc.getRemoteAddress());

                }
            }
        }

    }

    /**
     * 可以直接访问到内部类的 private 的成员变量
     */
    static class Worker implements Runnable {
        private Thread thread;
        private Selector selector;
        private String name;
        // 还未初始化
        private volatile boolean start = false;
        // 用队列来进行线程间的通信
        private ConcurrentLinkedQueue<Runnable> queue = new ConcurrentLinkedQueue<>();

        public Worker(String name) {
            this.name = name;
        }

        //初始化线程和selector
        public void register(SocketChannel sc) throws IOException {
            if (!start) {
                this.thread = new Thread(this, name);
                thread.start();
                selector = Selector.open();
                start = true;
            }
            // 唤醒select
            // 使当前被select（）方法阻塞的线程立刻返回，或者下一次调用select（）方法的线程不会阻塞
            selector.wakeup();
            sc.register(selector, SelectionKey.OP_READ, null);


        }

        @Override
        public void run() {
            while (true) {
                try {
                    // worker-0 阻塞，wakeup
                    selector.select();

                    Iterator<SelectionKey> iter = selector.selectedKeys().iterator();
                    while (iter.hasNext()) {
                        SelectionKey key = iter.next();
                        iter.remove();
                        if (key.isReadable()) {
                            ByteBuffer buffer = ByteBuffer.allocate(16);
                            SocketChannel channel = (SocketChannel) key.channel();
                            log.debug("read...{}", channel.getRemoteAddress());
                            channel.read(buffer);
                            buffer.flip();
                            debugAll(buffer);
                        }
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }

            }
        }
    }
}
