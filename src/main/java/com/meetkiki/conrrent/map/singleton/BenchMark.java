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
         * CPU AMD 4800H 8c16T
         * 内存 16G
         *
         *  runDoubleCheckSingleton
         * cost ms 4514
         *  runCasSingleton
         * cost ms 3730
         *  runAtomicReferenceSingleton
         * cost ms 3810
         *
         *  理论上来说 cas方式无锁竞争效率应该比有锁更高的，不会造成锁等待
         *      但实际上单例在构造时只会初始化一次，不会重复执行线程冲突的逻辑，
  3        *      并且不会有很高的并发，所以使用任何一种形式初始化单例只需要保证线程安全且唯一实例就可以了，
         *      不要求很高的效率上来说，我更推荐double check单例模式 或者枚举
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


    public static void executeOnce(int threadTask, Callable<AbstractSingleton> callable) {
        for (int i = 0; i < threadTask; i++) {
            FUTURES.add(executorService.submit(callable));
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
