package com.meetkiki.conrrent.aqs;

public class TestReentrantReadWriteLock {

    public static void main(String[] args) {
        ReentrantReadWriteLock lock = new ReentrantReadWriteLock();


        lock.readLock().lock();
    }

}
