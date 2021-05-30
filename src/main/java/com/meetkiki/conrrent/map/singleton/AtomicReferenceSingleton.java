package com.meetkiki.conrrent.map.singleton;

import java.util.concurrent.atomic.AtomicReference;

public class AtomicReferenceSingleton extends AbstractSingleton {


    private final String name;
    /**
     * 利用AtomicReference
     */
    private static final AtomicReference<AtomicReferenceSingleton> INSTANCE = new AtomicReference<>();

    /**
     * 私有化
     */
    private AtomicReferenceSingleton() {
        this.name = "atomic instance name";
    }

    /**
     * 用CAS确保线程安全
     *
     * @return
     */
    public static AtomicReferenceSingleton getInstance() {
        for (; ; ) {
            AtomicReferenceSingleton current = INSTANCE.get();
            if (current != null) {
                return current;
            }
            current = new AtomicReferenceSingleton();
            if (INSTANCE.compareAndSet(null, current)) {
                return current;
            }
        }
    }


    @Override
    public void clear() {
        INSTANCE.set(null);
    }
}