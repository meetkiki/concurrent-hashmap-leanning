package com.meetkiki.conrrent.aqs;

import org.openjdk.jol.info.ClassLayout;

public class TestMarkwordMain {

    private static final String SPLITE_STR = "===========================================";
    private static final User USER = new User();

    private static void printf() {
        System.out.println(SPLITE_STR);
        System.out.println(ClassLayout.parseInstance(USER).toPrintable());
        System.out.println(SPLITE_STR);
    }

    private static final Runnable RUNNABLE = () -> {
        while (!Thread.interrupted()) {
            synchronized (USER) {
                printf();
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 2; i++) {
            new Thread(RUNNABLE).start();
//            Thread.sleep(1000);
        }
        Thread.sleep(Integer.MAX_VALUE);
    }

    public static class User {
        private String name;
        private Integer age;
        private boolean sex;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Integer getAge() {
            return age;
        }

        public void setAge(Integer age) {
            this.age = age;
        }

        public boolean isSex() {
            return sex;
        }

        public void setSex(boolean sex) {
            this.sex = sex;
        }
    }

}
