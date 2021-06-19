package com.meetkiki.conrrent.aqs;


public class TestReentrantLock {


    public static void main(String[] args) {
        ReentrantLock lock = new ReentrantLock(true);

        Thread threadA = new Thread(getRunnable(lock, 100000), "threadA");
        Thread threadB = new Thread(getRunnable(lock, 100000), "threadB");
        Thread threadC = new Thread(getRunnable(lock, 10000), "threadC");

        threadA.start();
        threadB.start();
        threadC.start();
    }

    private static Runnable getRunnable(ReentrantLock lock, int time) {
        return () -> {
            try {
                lock.lock();
                System.out.println(Thread.currentThread().getName() + " locked --- ");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                lock.unlock();
            }
        };
    }

}
