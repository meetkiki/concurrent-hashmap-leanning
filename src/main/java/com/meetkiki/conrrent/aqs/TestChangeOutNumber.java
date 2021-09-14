package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.Condition;

public class TestChangeOutNumber {

    public static void main(String[] args) throws InterruptedException {
        ReentrantLock lock = new ReentrantLock();
        Condition lCondition = lock.newCondition();
        Condition rCondition = lock.newCondition();

        Thread threadA = new Thread(outPutTask(lock, lCondition, rCondition, 0));

        Thread threadB = new Thread(outPutTask(lock, rCondition, lCondition, 1));
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
    }

    private static Runnable outPutTask(ReentrantLock lock, Condition lCondition, Condition rCondition, int start) {
        return () -> {
            for (int i = start; i <= 100; i = i + 2) {
                lock.lock();
                try {
                    System.out.println(Thread.currentThread().getName() + " ==== number : " + i + " =====");
                    lCondition.signal();
                    if (i < 100) {
                        rCondition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        };
    }
}
