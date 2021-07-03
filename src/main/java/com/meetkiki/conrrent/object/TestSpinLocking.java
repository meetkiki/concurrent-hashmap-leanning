package com.meetkiki.conrrent.object;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class TestSpinLocking {

    public static void main(String[] args) throws InterruptedException {
        TestSpinLocking spinLocking = new TestSpinLocking();
        System.out.println("--MAIN--:" + ClassLayout.parseInstance(spinLocking).toPrintable());

        Thread thread1 = new Thread(() -> {
            /**
             * 第一个线程 拿到锁 因为虚拟机前4s 是不可偏向状态 这里拿到的是轻量级锁
             */
            synchronized (spinLocking) {
                System.out.println("--THREAD1--:" + ClassLayout.parseInstance(spinLocking).toPrintable());
                try {
                    TimeUnit.SECONDS.sleep(5);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("--THREAD1--:" + ClassLayout.parseInstance(spinLocking).toPrintable());
            }
        });
        Thread thread2 = new Thread(() -> {
            try {
                TimeUnit.SECONDS.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            /**
             * 休眠2s后 抢占锁 导致轻量级锁升级为重量级锁
             */
            synchronized (spinLocking) {
                System.out.println("--THREAD2--:" + ClassLayout.parseInstance(spinLocking).toPrintable());
            }
        });

        thread1.start();
        thread2.start();
        thread1.join();
        thread2.join();
        TimeUnit.SECONDS.sleep(3);
        System.out.println(ClassLayout.parseInstance(spinLocking).toPrintable());
    }

}
