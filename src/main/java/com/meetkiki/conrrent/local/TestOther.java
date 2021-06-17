package com.meetkiki.conrrent.local;

import java.util.concurrent.locks.ReentrantLock;

public class TestOther {

    private final ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        TestOther testOther = new TestOther();

        final ReentrantLock lock = testOther.lock;
        lock.lock();


        lock.unlock();
    }

}
