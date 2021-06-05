package com.meetkiki.conrrent.map.scene;

import org.springframework.cache.support.NullValue;
import org.springframework.lang.Nullable;
import org.springframework.util.Assert;

import java.util.concurrent.Callable;
import java.util.concurrent.ConcurrentMap;


public class ConcurrentMapCache {
    private final String name;
    private final boolean allowNullValues;
    private final ConcurrentMap<Object, Object> store;

    @SuppressWarnings("unchecked")
    public <T> T get(Object key, Callable<T> valueLoader) {
        Object storeValue = this.store.computeIfAbsent(key, k -> {
            try {
                T userValue = valueLoader.call();
                if (userValue == null) {
                    if (this.allowNullValues) {
                        return NullValue.INSTANCE;
                    }
                    throw new IllegalArgumentException("Cache '" + getName() + "' is configured to not allow null values but null was provided");
                }
                return userValue;
            } catch (Throwable ex) {
                throw new RuntimeException();
            }
        });
        if (this.allowNullValues && storeValue == NullValue.INSTANCE) {
            return null;
        }
        return (T) storeValue;
    }

    public void put(Object key, @Nullable Object value) {
        if (value == null) {
            if (this.allowNullValues) {
                value = NullValue.INSTANCE;
            } else {
                throw new IllegalArgumentException("Cache '" + getName() + "' is configured to not allow null values but null was provided");
            }
        }
        this.store.put(key, value);
    }

    protected ConcurrentMapCache(String name, ConcurrentMap<Object, Object> store,
                                 boolean allowNullValues) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(store, "Store must not be null");
        this.name = name;
        this.store = store;
        this.allowNullValues = allowNullValues;
    }


    public final String getName() {
        return this.name;
    }

}
