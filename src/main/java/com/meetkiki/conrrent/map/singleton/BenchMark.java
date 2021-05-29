package com.meetkiki.conrrent.map.singleton;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class BenchMark {


    public static void main(String[] args) {

        int threadSize = 100;
        int count = 200;

        System.out.println(" runCasSingleton  ");
        runCasSingleton(threadSize,count);

        System.out.println(" runSingleton  ");
        runSingleton(threadSize,count);

        System.out.println(" runAtomicReferenceSingleton  ");
        runAtomicReferenceSingleton(threadSize,count);
    }

    private static void runAtomicReferenceSingleton(int threadSize, int count) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            executeOnce(threadSize, CasSingleton::getInstance);
        }

        long end = System.currentTimeMillis();

        System.out.println("cost ms " + (end - start));
    }

    private static void runCasSingleton(int threadSize, int count) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            executeOnce(threadSize, CasSingleton::getInstance);
        }

        long end = System.currentTimeMillis();

        System.out.println("cost ms " + (end - start));
    }

    private static void runSingleton(int threadSize, int count) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            executeOnce(threadSize, Singleton::getInstance);
        }

        long end = System.currentTimeMillis();

        System.out.println("cost ms " + (end - start));
    }


    public static void executeOnce(int threadSize, Callable<AbstractSingleton> runnable) {
        for (int i = 0; i < threadSize; i++) {
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
