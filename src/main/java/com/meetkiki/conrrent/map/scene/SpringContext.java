package com.meetkiki.conrrent.map.scene;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class SpringContext {

    // 此处方便模拟扩容 设置容量参数为32
    private static final int INITIAL_CAPACITY = 32;
    private static final String PRO_ENV = "pro";
    private final ConcurrentMap<Object, Object> store = new ConcurrentHashMap<>(INITIAL_CAPACITY);
    // 模拟 Environment 环境配置本地缓存
    private final ConcurrentMapCache cache = new ConcurrentMapCache("cache", store, true);

    // 扩容时容量阈值
    private static final int RESIZE_CAPACITY = INITIAL_CAPACITY + (INITIAL_CAPACITY >>> 1);


    // 启动时 假设环境参数有这些 RESIZE_CAPACITY - 1 个
    {
        for (int i = 0; i < RESIZE_CAPACITY - 1; i++) {
            cache.put("cacheConfig " + i, "cacheValue");
        }
    }

    public static void main(String[] args) {
        new SpringContext().start();
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
     * 优先从缓存取 取不到从其他配置服务获取
     */
    private String getConfigFromEnv(String configKey) {
        return cache.get(configKey, () -> getConfigByOtherConfig(configKey));
    }

    /**
     * 模拟从其他配置服务拉取配置场景
     */
    private String getConfigByOtherConfig(String configKey) {
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

    /**
     * 优先从缓存取 取不到给dev
     */
    private String getSpringActive() {
        return cache.get("active", () -> "dev");
    }
}
