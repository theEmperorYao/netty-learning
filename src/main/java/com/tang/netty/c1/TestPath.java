package com.tang.netty.c1;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

/**
 * @Title: TestPath
 * @Description:
 * @author: tangyao
 * @date: 2022/4/26 18:07
 * @Version: 1.0
 */

public class TestPath {

    public static void main(String[] args) throws IOException {
        long l = System.nanoTime();
//        Path source = Paths.get("E:\\Code\\netty-learning\\data.txt");
//        Path target = Paths.get("E:\\Code\\netty-learning\\to.txt");
//        Files.copy(source, target, StandardCopyOption.REPLACE_EXISTING);
//

//        Path source = Paths.get("E:\\Code\\netty-learning\\to1.txt");
//        Path target = Paths.get("E:\\Code\\netty-learning\\to.txt");
//        Files.move(source, target, StandardCopyOption.ATOMIC_MOVE);

        Path target = Paths.get("E:\\Code\\netty-learning\\to.txt");
        Files.delete(target);


        long l1 = System.nanoTime() - l;
        System.out.println("l1 = " + l1 / 1000.0 / 1000 / 1000);
    }
}