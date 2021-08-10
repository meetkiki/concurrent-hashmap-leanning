package com.meetkiki.dp;

public class FibonacciTest {

    public static void main(String[] args) {
        System.out.println(new FibonacciTest().fibonacci(7));
    }


    public int fibonacci(int val){
        int[] dp = new int[val + 1];
        dp[0] = 0;
        dp[1] = 1;
        for (int i = 2; i <= val; i++) {
            dp[i] = dp[i - 1] + dp[i - 2];
        }
        return dp[val];
    }

}
