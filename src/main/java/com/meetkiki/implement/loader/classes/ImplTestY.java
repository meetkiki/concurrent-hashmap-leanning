package com.meetkiki.implement.loader.classes;

public class ImplTestY implements InterfaceA {

    @Override
    public void test() {
        System.out.println(this.getClass().getSimpleName() + " test ...");
    }

}
