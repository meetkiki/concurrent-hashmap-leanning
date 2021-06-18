package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ChangeOutputTest {

    static boolean[] start = new boolean[1];

    public static void main(String[] args) {
        String number = "1234567890";
        String letter = "abcdefghijklmnopqrstuvwxyz";
        char[] numberChars = number.toCharArray();
        char[] letterChars = letter.toCharArray();

        ReentrantLock lock = new ReentrantLock();
        Condition nCondition = lock.newCondition();
        Condition lCondition = lock.newCondition();

        new Thread(() -> {
            // 等待一个start的信号
            while (!start[0]){
                try {
                    Thread.sleep(1);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            for (int i = 0; i < letterChars.length; i++) {
                lock.lock();
                try {
                    System.out.println("==== letter : " + letterChars[i] + " =====");
                    nCondition.signal();
                    if (i < numberChars.length - 1) {
                        lCondition.await();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();


        new Thread(() -> {
            for (int i = 0; i < numberChars.length; i++) {
                lock.lock();
                try {
                    System.out.println("==== number : " + numberChars[i] + " =====");
                    start[0] = true;
                    lCondition.signal();
                    nCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();


    }


}
