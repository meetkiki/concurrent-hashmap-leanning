package com.meetkiki.conrrent.object;

public class TestVolatileStop implements Runnable {

    private boolean stop;

    private volatile boolean s = false;

    TestVolatileStop(boolean status) {
        this.stop = status;
    }

    @Override
    public void run() {
        while (!stop) {
            boolean a = s;
//            System.out.println("----");
        }
        long time = System.nanoTime();
        System.out.println("stop === " + time);
    }

    public void setStop(boolean stop) {
        this.stop = stop;
    }

    public static void main(String[] args) throws InterruptedException {
        // 测试代码
        TestVolatileStop testVolatileStop = new TestVolatileStop(false);
        new Thread(testVolatileStop).start();
        Thread.sleep(100); // 等待线程执行
        testVolatileStop.setStop(true);
        long time = System.nanoTime();
        System.out.println("end  === " + time);
    }
}




