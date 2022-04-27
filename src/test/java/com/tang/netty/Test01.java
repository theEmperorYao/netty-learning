package com.tang.netty;


import cn.hutool.core.clone.CloneRuntimeException;
import cn.hutool.core.clone.CloneSupport;
import cn.hutool.core.clone.Cloneable;
import cn.hutool.core.io.file.FileWriter;
import cn.hutool.core.util.ObjectUtil;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;
import java.util.concurrent.*;

/**
 * @Title: Test01
 * @Description:
 * @author: tangyao
 * @date: 2022/4/8 17:15
 * @Version: 1.0
 */
@SpringBootTest
@Slf4j
public class Test01 {

    @Test
    void test04() {
        int i = 10 >> 2;
        int a = 10 << 2;
        int mid = 10 + ((20 - 10) >> 1);
        System.out.println("mid = " + mid);
        System.out.println("a = " + a);
        System.out.println("i = " + i);
    }

    @Test
    void test03() throws IOException {
        Path path = Paths.get("E:\\Code\\netty-learning\\helloworld\\a\\b");
        Files.createDirectories(path);
    }

    @Test
    void test02() {

        CountDownLatch countDownLatch = new CountDownLatch(100);
        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(100, 100, 1, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));

        for (int j = 0; j < 100; j++) {
//            CompletableFuture.runAsync(() -> {
            StringBuilder stringBuilder = new StringBuilder();
            for (int i = 0; i < 1000000; i++) {
                stringBuilder.append(UUID.randomUUID());
            }
            FileWriter writer = new FileWriter("E:\\Code\\netty-learning\\data.txt");
            writer.write(stringBuilder.toString(), true);
//            },threadPoolExecutor).whenComplete((a,b)->{
//                countDownLatch.countDown();
//            });

        }


        System.out.println();


    }

    @Data
    private static class Cat implements Cloneable<Cat> {
        private String name = "miaomiao";
        private int age = 2;

        @Override
        public Cat clone() {
            try {
                return (Cat) super.clone();
            } catch (CloneNotSupportedException e) {
                throw new CloneRuntimeException(e);
            }
        }
    }

    @Data
    private static class Dog extends CloneSupport<Dog> {
        private String name = "wangwang";
        private int age = 3;
    }

    @Test
    void test01() {

        Cat cat = new Cat();
        cat.setAge(10);


        Cat clone = cat.clone();
        cat.setName("aaa");
        System.out.println("cat = " + cat);
        System.out.println("clone = " + clone);


    }


}