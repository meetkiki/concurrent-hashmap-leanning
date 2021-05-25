package com.meetkiki.conrrent.map;

import java.util.concurrent.ConcurrentHashMap;

public class ConcurrentHashMapTest {

    public static void main(String[] args) {
        ConcurrentHashMap<String, String> concurrentHashMap = new ConcurrentHashMap<>();

        for (int i = 0; i < 100; i++) {
            concurrentHashMap.put("1" + i, "1");
        }

        System.out.println(concurrentHashMap.size() + " count");
    }


}
