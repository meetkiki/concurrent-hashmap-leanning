package com.meetkiki.conrrent.threadpool;

public class CPUUtilizationTest {
    public static void main(String[] args) throws InterruptedException {

        for (int j = 0; j < 32; j++) {
            new Thread(() -> {
                while (true) {
                }
            }).start();
        }

        Thread.sleep(10000);


        System.exit(-1);
    }
}