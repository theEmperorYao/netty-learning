package com.tang.netty.nio.c1;

import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

/**
 * @Classname TestFilesCopy
 * @Description TODO
 * @Date 2022/4/26 21:05
 * @Author by tangyao
 */
public class TestFilesCopy {

    public static void main(String[] args) throws IOException {
        String source = "/Users/tangyao/Code/java/netty/hello/a";
        String target = "/Users/tangyao/Code/java/netty/hello1/b";

        m3(target);

        Files.walk(Paths.get(source)).forEach(path -> {
            // ctrl + alt + T
            try {
                String targetName = path.toString().replace(source, target);
                // 是目录
                if (Files.isDirectory(path)) {

                    Files.createDirectories(Paths.get(targetName));
                } else if (Files.isRegularFile(path)) {
                    Files.copy(path, Paths.get(targetName));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public static void m3(String path) throws IOException {
        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {


            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                System.out.println(file);
                Files.delete(file);
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                System.out.println("=====> 退出 " + dir);
                Files.delete(dir);
                return super.postVisitDirectory(dir, exc);
            }
        });
    }

}
