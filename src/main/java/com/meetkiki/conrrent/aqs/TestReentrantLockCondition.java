package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.Condition;

public class TestReentrantLockCondition {

    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();

    public void await() {
        lock.lock();
        try {
            System.out.println("线程获取锁----" + Thread.currentThread().getName());
            condition.await(); //调用await()方法 会释放锁，和Object.wait()效果一样。
            System.out.println("线程被唤醒----" + Thread.currentThread().getName());
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("线程释放锁----" + Thread.currentThread().getName());
        }
    }

    public void signal() {
        try {
            Thread.sleep(1000);  //休眠1秒钟 等等一个线程先执行
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        lock.lock();
        try {
            System.out.println("另外一个线程获取到锁----" + Thread.currentThread().getName());
            condition.signal();
            System.out.println("唤醒线程----" + Thread.currentThread().getName());

            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            lock.unlock();
            System.out.println("另外一个线程释放锁----" + Thread.currentThread().getName());
        }
    }

    public static void main(String[] args) {
        TestReentrantLockCondition t = new TestReentrantLockCondition();
        Thread t1 = new Thread(t::await);

        Thread t2 = new Thread(t::signal);

        t1.start();
        t2.start();
    }


}
