package com.meetkiki.conrrent.object;

import org.openjdk.jol.info.ClassLayout;

import java.util.concurrent.TimeUnit;

public class TestBiasedLocking2 {

    public static void main(String[] args) throws InterruptedException {
        TestBiasedLocking locking = new TestBiasedLocking();
        System.out.println("start ==== " + ClassLayout.parseInstance(locking).toPrintable());

        Thread thread = new Thread(() -> {
            synchronized (locking) {
                System.out.println("--THREAD1--:" + ClassLayout.parseInstance(locking).toPrintable());
                try {
                    locking.wait(2000);
//                    locking.hashCode();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                System.out.println("--THREAD END--:" + ClassLayout.parseInstance(locking).toPrintable());
            }
        });
        thread.start();
        thread.join();
        TimeUnit.SECONDS.sleep(3);
        System.out.println("end ==== " + ClassLayout.parseInstance(locking).toPrintable());
    }

}
