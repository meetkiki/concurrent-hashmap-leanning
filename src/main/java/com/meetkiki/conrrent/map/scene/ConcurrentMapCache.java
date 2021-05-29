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

    protected ConcurrentMapCache(String name, ConcurrentMap<Object, Object> store,
                                 boolean allowNullValues) {
        Assert.notNull(name, "Name must not be null");
        Assert.notNull(store, "Store must not be null");
        this.name = name;
        this.store = store;
        this.allowNullValues = allowNullValues;
    }

    @SuppressWarnings("unchecked")
    @Nullable
    public <T> T get(Object key, Callable<T> valueLoader) {
        return (T) fromStoreValue(this.store.computeIfAbsent(key, k -> {
            try {
                return toStoreValue(valueLoader.call());
            } catch (Throwable ex) {
                throw new RuntimeException();
            }
        }));
    }

    public void put(Object key, @Nullable Object value) {
        this.store.put(key, toStoreValue(value));
    }


    protected Object toStoreValue(@Nullable Object userValue) {
        return superToStoreValue(userValue);
    }

    protected Object fromStoreValue(@Nullable Object storeValue) {
        return superFromStoreValue(storeValue);
    }

    @Nullable
    protected Object superFromStoreValue(@Nullable Object storeValue) {
        if (this.allowNullValues && storeValue == NullValue.INSTANCE) {
            return null;
        }
        return storeValue;
    }

    protected Object superToStoreValue(@Nullable Object userValue) {
        if (userValue == null) {
            if (this.allowNullValues) {
                return NullValue.INSTANCE;
            }
            throw new IllegalArgumentException(
                    "Cache '" + getName() + "' is configured to not allow null values but null was provided");
        }
        return userValue;
    }

    public final String getName() {
        return this.name;
    }

}
