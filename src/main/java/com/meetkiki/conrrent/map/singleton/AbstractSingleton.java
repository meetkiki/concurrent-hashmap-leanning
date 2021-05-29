package com.meetkiki.conrrent.map.singleton;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.ThreadPoolExecutor;

public abstract class AbstractSingleton {

    AbstractSingleton(){
    }

    public static int THREAD_SIZE = 1000;

    public static ThreadPoolExecutor executorService = (ThreadPoolExecutor) Executors.newFixedThreadPool(THREAD_SIZE);

    public static Set<AbstractSingleton> SET = new HashSet<>();

    public static List<Future<AbstractSingleton>> FUTURES = new ArrayList<>();

    public abstract void clear();
}
