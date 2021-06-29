package com.meetkiki.conrrent.object;

import org.openjdk.jol.info.ClassLayout;
import org.openjdk.jol.info.GraphLayout;

import java.util.HashMap;

public class HashMapTest {

    public static void main(String[] args) {
        HashMap<String, String> hashMap = new HashMap<>();
        hashMap.put("test", "www.test.com");
        System.out.println(GraphLayout.parseInstance(hashMap).toPrintable());
        System.out.println(ClassLayout.parseInstance(hashMap).toPrintable());
    }

}
