package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.ReentrantLock;

public class TestReentrantLock {


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);

        new Thread(() -> {
            try {
                lock.lock();

                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();

        new Thread(() -> {
            try {
                lock.lock();

                Thread.sleep(1000000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


        new Thread(() -> {
            try {
                lock.lock();

                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        }).start();


    }

}
