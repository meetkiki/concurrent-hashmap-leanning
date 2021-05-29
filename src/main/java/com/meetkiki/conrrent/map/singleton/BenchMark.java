package com.meetkiki.conrrent.map.singleton;

import java.util.Optional;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

public class BenchMark {


    public static void main(String[] args) {
        int threadSize = 200;
        int count = 10000;

        System.out.println(" runSingleton  ");
        runSingleton(threadSize, count, Singleton::getInstance);

        System.out.println(" runCasSingleton  ");
        runSingleton(threadSize, count, CasSingleton::getInstance);

        System.out.println(" runAtomicReferenceSingleton  ");
        runSingleton(threadSize, count, AtomicReferenceSingleton::getInstance);
    }


    private static void runSingleton(int threadSize, int count, Callable<AbstractSingleton> callable) {
        long start = System.currentTimeMillis();

        for (int i = 0; i < count; i++) {
            executeOnce(threadSize, callable);
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
