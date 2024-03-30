package com.example.basiccalculatorapp;

import java.util.Stack;

public class Calculator {
    public static String getResult(String expression) {
        try {
            return evaluate(expression);
        } catch (Exception e) {
            return "Error";
        }
    }
    public static String evaluate(String expression) {
        // Create a stack to store operands and operators
        Stack<Double> values = new Stack<>();
        Stack<Character> ops = new Stack<>();

        // If * and / are present in the leftmost part of the expression
        if (expression.charAt(0) == '*' || expression.charAt(0) == '/')
            return "Invalid expression";

        // Split the expression into tokens
        String[] tokens = expression.split(" ");

        // Iterate over the expression
        for (String token : tokens) {
            // If the token is a number, push it to the values stack
            if (!isOperator(token)) {
                values.push(Double.parseDouble(token));
            } else {
                // If the token is an operator, push it to the ops stack
                ops.push(token.charAt(0));
            }
        }

        // Iterate over the operators stack
        while (!ops.isEmpty()) {
            // Pop the operator
            char op = ops.pop();

            // Pop the operands
            double val1 = values.pop();
            double val2 = values.pop();

            // Apply the operator to the operands
            // Here we also need to check for precedence
            if (!ops.isEmpty() && hasPrecedence(op, ops.peek())) {
                values.push(applyOp(ops.pop(), val2, values.pop())); // Apply the operator to the second and third operands
                values.push(val1); // Push the first operand back to the values stack to be used later
                ops.push(op); // Push the operator back to the ops stack to be used later
            } else {
                values.push(applyOp(op, val1, val2));
            }
        }

        // Return the final result
        return formatResult(values.pop());
    }

    private static String formatResult(Double pop) {
        if (pop.isNaN()) return "Error";
        else if (pop % 1 == 0) return String.valueOf((int) Math.round(pop));
        else return String.valueOf(pop);
    }

    private static boolean isOperator(String token) {
        return token.equals("+") || token.equals("-") || token.equals("*") || token.equals("/");
    }

    private static Double applyOp(Character pop, Double pop1, Double pop2) {
        switch (pop) {
            case '+':
                return pop2 + pop1;
            case '-':
                return pop2 - pop1;
            case '*':
                return pop2 * pop1;
            case '/':
                if (pop1 == 0)
                    return Double.NaN;
                return pop2 / pop1;
        }
        return 0.0;
    }

    private static boolean hasPrecedence(char token, Character peek) {
        if ((token == '*' || token == '/') && (peek == '+' || peek == '-'))
            // If the token is * or / and the peek is + or -, return true
            return false;
        return true;
    }
}