package com.meetkiki.conrrent.aqs;

import java.util.concurrent.locks.LockSupport;

public class TestInterrupted {

    public static void main(String[] args) throws InterruptedException {


        Thread thread = new Thread() {
            @Override
            public void run() {
                System.out.println("start ==== ");

                LockSupport.park();

                System.out.println("end ==== ");
            }
        };
        thread.start();

//        thread.interrupt();

        thread.join();
    }

}
