package com.meetkiki.conrrent.map.singleton;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

public class CasSingleton extends AbstractSingleton {

    private String name;

    public static final int FINISH = 2;

    // 未初始化0 实例化中 1 实例化完成 2
    private static volatile int init = 0;

    private static volatile CasSingleton INSTANCE;

    private CasSingleton() {
        this.name = "cas instance name";
    }

    public static CasSingleton getInstance() {
        if (INSTANCE == null) {
            for (; ; ) {
                if (U.compareAndSwapInt(CasSingleton.class, SIZE_INIT, 0, 1)) {
                    try {
                        if (INSTANCE == null) {
                            INSTANCE = new CasSingleton();
                        }
                        break;
                    } finally {
                        init = FINISH;
                    }
                } else if (init == FINISH){
                    return INSTANCE;
                } else {
                    Thread.yield();
                }
            }
        }
        return INSTANCE;
    }


    public void clear() {
        INSTANCE = null;
        SET.clear();
        FUTURES.clear();
        U.putIntVolatile(CasSingleton.class, SIZE_INIT, 0);
    }

    @Override
    public String toString() {
        return "CasSingleton{" +
                "name='" + name + '\'' +
                '}';
    }

    // Unsafe mechanics
    private static final Unsafe U;
    private static final long SIZE_INIT;

    static {
        try {
            Field field = Unsafe.class.getDeclaredField("theUnsafe");
            field.setAccessible(true);
            U = (Unsafe) field.get(null);
            Class<?> k = CasSingleton.class;
            SIZE_INIT = U.staticFieldOffset
                    (k.getDeclaredField("init"));
        } catch (Exception e) {
            throw new Error(e);
        }
    }

}
