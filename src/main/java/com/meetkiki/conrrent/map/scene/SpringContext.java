package com.meetkiki.conrrent.map.scene;

import org.springframework.cache.concurrent.ConcurrentMapCache;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SpringContext {

    public static final String CACHE_KEY = "cache";
    // 此处方便模拟扩容 设置容量参数为32
    public static final int INITIAL_CAPACITY = 32;
    public static final String PRO_ENV = "pro";
    public final ConcurrentMap<Object, Object> store = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    // 模拟 Environment 环境配置
    public final ConcurrentMapCache cache = new ConcurrentMapCache(CACHE_KEY, store, true);

    // 扩容时容量阈值
    public static final int RESIZE_CAPACITY = INITIAL_CAPACITY + (INITIAL_CAPACITY >>> 1);


    // 启动时 假设环境参数有这些 CTL_CAPACITY - 1 个
    {
        for (int i = 0; i < RESIZE_CAPACITY - 1; i++) {
            cache.put("cacheConfig " + i, "cacheValue");
        }
    }

    /**
     * 优先从缓存取 取不到给dev
     */
    private String getSpringActive() {
        return cache.get("active", () -> "dev");
    }

    /**
     * 容器启动方法
     */
    public void start() {
        System.out.println("============ start ============");

        String mainConfig = getConfigFromEnv("mainConfig");

        System.out.println("start up at mainConfig: " + mainConfig);

        System.out.println("============= end =============");
    }

    /**
     * 优先从缓存取 取不到从Apollo获取
     */
    private String getConfigFromEnv(String configKey) {
        return cache.get(configKey, () -> getConfigByApollo(configKey));
    }

    /**
     * 模拟从apollo拉取配置场景
     */
    private String getConfigByApollo(String configKey) {
        String active = System.getenv(configKey);

        /**
         * 如果是生产 对加密的部分进行解密
         */
        if (PRO_ENV.equals(getSpringActive())) {
            System.out.println("decrypt config ...");
            return active;
        }

        return active;
    }




    public static void main(String[] args) {
        new SpringContext().start();
    }


}
