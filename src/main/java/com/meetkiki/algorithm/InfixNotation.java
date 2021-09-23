package com.meetkiki.algorithm;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class InfixNotation {

    private Map<Character, Integer> opLevel = new HashMap<Character, Integer>() {{
        put('+', 1);
        put('-', 1);
        put('*', 2);
        put('/', 2);
    }};

    public static void main(String[] args) {
        String ss = "1-2+3*4-5";
        int res = new InfixNotation().calculate(ss);
        System.out.println(res);
    }

    private int calculate(String input) {
        if (input == null || input.isEmpty()) {
            return 0;
        }
        Stack<Integer> number = new Stack<>();
        Stack<Character> ope = new Stack<>();
        for (int i = 0; i < input.length(); i++) {
            char cur = input.charAt(i);
            if (Character.isDigit(cur)) {
                number.push(cur - '0');
            } else if (isOperator(cur)) {
                while (!ope.isEmpty() && opLevel.get(ope.peek()) >= opLevel.get(cur)) {
                    number.push(calculate(number, ope));
                }
                ope.push(cur);
            }
        }
        while (!ope.isEmpty()) {
            number.push(calculate(number, ope));
        }
        return number.peek();
    }

    private Integer calculate(Stack<Integer> number, Stack<Character> ope) {
        Character op = ope.pop();
        Integer n2 = number.pop();
        Integer n1 = number.pop();
        switch (op) {
            case '+':
                return n1 + n2;
            case '-':
                return n1 - n2;
            case '*':
                return n1 * n2;
            case '/':
                return n1 / n2;
        }
        throw new IllegalArgumentException();
    }

    private boolean isOperator(char cur) {
        return cur == '+' || cur == '-' || cur == '*' || cur == '/';
    }

}
