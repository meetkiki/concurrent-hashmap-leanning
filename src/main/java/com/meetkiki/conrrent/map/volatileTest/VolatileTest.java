package com.meetkiki.conrrent.map.volatileTest;

import java.util.concurrent.TimeUnit;

public class VolatileTest {

    /**
     * volatile 修饰数组 对数组元素有效吗？
     *
     *  答案是：有效的
     *
     *  If Thread A reads a volatile variable, then all all variables
     *  visible to Thread A when reading the volatile variable
     *  will also be re-read from main memory.
     *
     *  stackoverflow 解答
     *  https://stackoverflow.com/questions/53753792/java-volatile-array-my-test-results-do-not-match-the-expectations
     */
    public static volatile long[] arr = new long[20];

    public static void main(String[] args) throws Exception {
        //线程1
        new Thread(new Thread(() -> {
            //Thread A
            try {
                TimeUnit.MILLISECONDS.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            arr[19] = 2;
        })).start();
        //线程2
        new Thread(new Thread(() -> {
            //Thread B
            while (arr[19] != 2) {
            }
            System.out.println("Jump out of the loop!");
        })).start();
    }
}
