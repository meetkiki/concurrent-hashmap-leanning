package com.meetkiki.conrrent.map.singleton;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class BenchMark {


    public static void main(String[] args) {
        int threadTask = 1000;
        int count = 10000;

        AbstractSingleton.executorService.prestartAllCoreThreads();

        sleep();

        System.out.println(" runSingleton  ");
        runSingleton(threadTask, count, Singleton::getInstance);

        System.out.println(" runCasSingleton  ");
        runSingleton(threadTask, count, CasSingleton::getInstance);

        System.out.println(" runAtomicReferenceSingleton  ");
        runSingleton(threadTask, count, AtomicReferenceSingleton::getInstance);
    }

    private static void sleep() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }


    private static void runSingleton(int threadTask, int count, Callable<AbstractSingleton> callable) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            executeOnce(threadTask, callable);
        }

        long end = System.currentTimeMillis();

        System.out.println("cost ms " + (end - start));
    }


    public static void executeOnce(int threadTask, Callable<AbstractSingleton> runnable) {
        for (int i = 0; i < threadTask; i++) {
            AbstractSingleton.FUTURES.add(AbstractSingleton.executorService.submit(runnable));
        }

        for (Future<AbstractSingleton> future : AbstractSingleton.FUTURES) {
            try {
                AbstractSingleton.SET.add(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (AbstractSingleton.SET.size() != 1) {
            throw new RuntimeException("execute error.");
        }

        Optional<AbstractSingleton> first = AbstractSingleton.SET.stream().findFirst();
        first.ifPresent(AbstractSingleton::clear);
    }


}
