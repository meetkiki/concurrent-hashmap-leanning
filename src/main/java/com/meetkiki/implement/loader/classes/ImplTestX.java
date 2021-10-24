package com.meetkiki.implement.loader.classes;

public class ImplTestX implements InterfaceA {

    static {
        System.out.println("static ImplTestX");
    }

    {
        System.out.println("pre init ImplTestX");
    }

    public ImplTestX() {
        System.out.println("init ImplTestX");
    }

    @Override
    public void test() {
        System.out.println(this.getClass().getSimpleName() + " test ...");
    }
}
