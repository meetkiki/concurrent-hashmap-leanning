package com.meetkiki.conrrent.aqs;

public class SemaphoreTest {

    @SuppressWarnings("DuplicatedCode")
    public static void main(String[] args) {

        Semaphore semaphore = new Semaphore(2, true);

        Thread threadA = new Thread(getRunnable(semaphore, 1000), "threadA");
        Thread threadB = new Thread(getRunnable(semaphore, 1000), "threadB");
        Thread threadC = new Thread(getRunnable(semaphore, 1000), "threadC");
        Thread threadD = new Thread(getRunnable(semaphore, 1000), "threadD");
        Thread threadE = new Thread(getRunnable(semaphore, 1000), "threadE");

        threadA.start();
        threadB.start();
        threadC.start();
        threadD.start();
        threadE.start();
    }


    private static Runnable getRunnable(Semaphore semaphore, int time) {
        return () -> {
            try {
                semaphore.acquire();
                System.out.println(Thread.currentThread().getName() + " acquired --- ");
                Thread.sleep(time);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                semaphore.release();
                System.out.println(Thread.currentThread().getName() + " released --- ");
            }
        };
    }

}
