package com.meetkiki.conrrent.aqs;

public class TestChangeOutputCas {

    private static volatile boolean out = false;

    public static void main(String[] args) throws InterruptedException {
        String number = "1234567890";
        String letter = "abcdefghijklmnopqrstuvwxyz";
        char[] numberChars = number.toCharArray();
        char[] letterChars = letter.toCharArray();

        Thread threadA = new Thread(() -> {
            for (int i = 0; i < numberChars.length; i++) {
                while (out) {
                    // empty 893900
                    // 1627900
//                    Thread.yield();
                    // 1809700
//                    try {
//                        Thread.sleep(0);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                System.out.println("==== number : " + numberChars[i] + " =====");
                out = true;
            }
        });


        Thread threadB = new Thread(() -> {
            for (int i = 0; i < letterChars.length; i++) {
                while (!out) {
//                    Thread.yield();
//                    try {
//                        Thread.sleep(0);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
                }
                System.out.println("==== letter : " + letterChars[i] + " =====");
                if (i < numberChars.length - 1) {
                    out = false;
                }
            }
        });


        final long start = System.nanoTime();
        threadA.start();
        threadB.start();
        threadA.join();
        threadB.join();
        final long end = System.nanoTime();
        System.out.println("耗时：" + (end - start));
    }

}
