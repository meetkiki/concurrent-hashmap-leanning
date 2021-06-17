package com.meetkiki.conrrent.local;

import java.util.concurrent.locks.ReentrantLock;

public class TestLocal {

    private final ReentrantLock lock = new ReentrantLock();


    public static void main(String[] args) {
        TestLocal testLocal = new TestLocal();

        testLocal.lock.lock();


        testLocal.lock.lock();
    }

}
