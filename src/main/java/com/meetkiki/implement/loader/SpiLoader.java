package com.meetkiki.implement.loader;

import com.meetkiki.implement.loader.classes.InterfaceA;

import java.util.ServiceLoader;

public class SpiLoader {


    public static void main(String[] args) {
        final ServiceLoader<InterfaceA> serviceLoader = ServiceLoader.load(InterfaceA.class);

        for (InterfaceA impl : serviceLoader) {
            impl.test();
        }
    }

}
