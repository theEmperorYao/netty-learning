package com.tang.netty.nio.c1;


import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: TestFilesWalkFileTree
 * @Description:
 * @author: tangyao
 * @date: 2022/4/26 18:25
 * @Version: 1.0
 */

public class TestFilesWalkFileTree {
    public static void main(String[] args) throws IOException {

//        m1();
//        m2();
        m3();

    }

    public static void m3() throws IOException {
        Files.walkFileTree(Paths.get("E:\\Code\\netty-learning\\helloworld"), new SimpleFileVisitor<Path>() {


            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
//                System.out.println(file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
//                System.out.println("=====> 退出 " + dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

    private static void m2() throws IOException {
        AtomicInteger javaCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("E:\\Code\\netty-learning"), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (file.toString().endsWith(".java")) {
                    System.out.println(file);
                    javaCount.getAndIncrement();
                }
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(javaCount.get());
    }

    private static void m1() throws IOException {
        AtomicInteger dirCount = new AtomicInteger();
        AtomicInteger fileCount = new AtomicInteger();
        Files.walkFileTree(Paths.get("E:\\Code\\netty-learning"), new SimpleFileVisitor<Path>() {


            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                System.out.println("=========>" + dir);
                dirCount.getAndIncrement();
                return super.preVisitDirectory(dir, attrs);
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println("=========>" + file);
                fileCount.getAndIncrement();
                return super.visitFile(file, attrs);
            }
        });
        System.out.println(fileCount.get());
        System.out.println(dirCount.get());
    }
}