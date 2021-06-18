package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ChangeOutputTest {

    public static void main(String[] args) {
        String number = "1234567890";
        String letter = "abcdefghijklmnopqrstuvwxyz";
        char[] numberChars = number.toCharArray();
        char[] letterChars = letter.toCharArray();

        ReentrantLock lock = new ReentrantLock();
        Condition nCondition = lock.newCondition();
        Condition lCondition = lock.newCondition();

        new Thread(() -> {
            for (int i = 0; i < numberChars.length; i++) {
                lock.lock();
                try {
                    System.out.println("==== number : " + numberChars[i] + " =====");
                    lCondition.signal();
                    nCondition.await();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    lock.unlock();
                }
            }
        }).start();

        new Thread(() -> {
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
    }


}
