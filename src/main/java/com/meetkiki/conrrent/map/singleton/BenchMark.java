package com.meetkiki.conrrent.map.singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public class BenchMark {


    public static void main(String[] args) {
        int threadTask = 1000;
        int count = 10000;

        executorService.prestartAllCoreThreads();

        /**
         * JDK 8u251
         * threadTask   1000
         * count        10000
         *
         *  runSingleton
         * cost ms 4457
         *  runCasSingleton
         * cost ms 4032
         *  runAtomicReferenceSingleton
         * cost ms 4142
         */

        System.out.println(" runDoubleCheckSingleton  ");
        runSingleton(threadTask, count, DoubleCheckSingleton::getInstance);

        System.out.println(" runCasSingleton  ");
        runSingleton(threadTask, count, CasSingleton::getInstance);

        System.out.println(" runAtomicReferenceSingleton  ");
        runSingleton(threadTask, count, AtomicReferenceSingleton::getInstance);
    }


    public static int THREAD_SIZE = 1000;

    public static ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_SIZE);

    public static Set<AbstractSingleton> SET = new HashSet<>();

    public static List<Future<AbstractSingleton>> FUTURES = new ArrayList<>();


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
            FUTURES.add(executorService.submit(runnable));
        }

        for (Future<AbstractSingleton> future : FUTURES) {
            try {
                SET.add(future.get());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        if (SET.size() != 1) {
            throw new RuntimeException("execute error.");
        }

        Optional<AbstractSingleton> first = SET.stream().findFirst();
        first.ifPresent(AbstractSingleton::clear);

        SET.clear();
        FUTURES.clear();
    }


}
