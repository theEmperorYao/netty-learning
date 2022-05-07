package com.tang.netty.nio.c1;


import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * @Title: TestChannelTransferTo
 * @Description:
 * @author: tangyao
 * @date: 2022/4/26 10:54
 * @Version: 1.0
 */

public class TestChannelTransferTo {
    public static void main(String[] args) {
        long l = System.nanoTime();
        try (FileChannel from = new FileInputStream("data.txt").getChannel();
             FileChannel to = new FileOutputStream("to.txt").getChannel();) {

            long size = from.size();
            // left 变量代表还剩余多少字节
            for (long left = size; left > 0; ) {
                System.out.println("position:" + (size - left) + " left:" + left);
                left -= from.transferTo(size - left, left, to);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        long l1 = System.nanoTime() - l;
        System.out.println("l1 = " + l1 / 1000.0 / 1000 / 1000);
    }
}