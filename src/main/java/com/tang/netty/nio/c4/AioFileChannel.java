package com.tang.netty.nio.c4;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousFileChannel;
import java.nio.channels.CompletionHandler;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.concurrent.CountDownLatch;

import static com.tang.netty.nio.c1.ByteBufferUtil.debugAll;

/**
 * @Classname AioFileChannel
 * @Description TODO
 * @Date 2022/5/3 22:58
 * @Author by tangyao
 */
@Slf4j
public class AioFileChannel {
    public static void main(String[] args) {

        try (AsynchronousFileChannel channel = AsynchronousFileChannel.open(Paths.get("word2.txt"), StandardOpenOption.READ)) {
            CountDownLatch countDownLatch = new CountDownLatch(1);
            ByteBuffer buffer = ByteBuffer.allocate(16);
            log.debug("read begin");
            channel.read(buffer, 0, buffer, new CompletionHandler<Integer, ByteBuffer>() {
                @Override
                public void completed(Integer result, ByteBuffer attachment) {
                    // 这里执行的线程是守护线程，如果其他线程运行完，他也会结束。
                    log.debug("read completed");
                    attachment.flip();
                    debugAll(attachment);
                    countDownLatch.countDown();
                }

                @Override
                public void failed(Throwable exc, ByteBuffer attachment) {
                    exc.printStackTrace();
                }
            });
            log.debug("read end...");
//            countDownLatch.await();
            System.in.read();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
