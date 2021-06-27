package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.LockSupport;

public class TestInterrupted {

    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread(() -> {
            System.out.println("start ==== ");

            LockSupport.park();

            System.out.println(Thread.interrupted());

            LockSupport.park();

            System.out.println(Thread.interrupted());

            System.out.println("end ==== ");
        });
        thread.start();
        Thread.sleep(100);
        /**
         * 这里会在LockSupport.park()第一次执行时中断 即13行会立刻响应
         *  此时的Thread.interrupted() 返回true
         * 但是第二次LockSupport.park()会清除标志位
         */
        thread.interrupt();

        Thread.sleep(100);
        // 唤醒17行代码处 即证明标志位已经被清除
        LockSupport.unpark(thread);
        thread.join();
    }

}
