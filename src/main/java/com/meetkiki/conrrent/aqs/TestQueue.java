package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.LockSupport;

public class TestQueue {

    public static void main(String[] args) {

        SingletonQueue<Integer> queue = new SingletonQueue<>();

        LockSupport.park();
        queue.add(1);
        queue.add(2);
        queue.add(3);
        queue.add(4);
        queue.add(5);

        for (Integer integer : queue) {
            System.out.println(" iii " + integer);
        }

        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
        System.out.println(queue.poll());
    }



}
