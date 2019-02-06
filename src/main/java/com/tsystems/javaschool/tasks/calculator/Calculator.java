package com.tsystems.javaschool.tasks.calculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.EmptyStackException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Stack;

public class Calculator {

    /**
     * Check if input token is operator.
     *
     * @param token token from infix expression.
     *
     * @return boolean variable. If token is operator - true. Otherwise - false.
     *
     *
     * **/
    private static boolean isOperator(String token){
        String[] operators = {"+","-","*","/","(",")"};
        List<String> listOfOperators = Arrays.asList(operators);
        if(listOfOperators.contains(token)){
            return true;
        }
        return false;
    }

    /**
     * Check if token is number
     *
     * @param token token from infix expression.
     * @return boolean variable. If token is number. Otherwise - false.
     */

    private static boolean isNumber(String token) {
        try{
            Double.valueOf(token);
            return true;
        } catch(Exception e){
            return false;
        }
    }

    /**
     *
     * @param infixNotation list of String that represent infix notation
     * @throws EmptyStackException,IllegalArgumentException if infix expression not valid.
     * @return Queue that represent postfix notation
     * */
    private Queue<String> buildRPN(ArrayList<String> infixNotation) throws EmptyStackException,IllegalArgumentException{
        Map<String, Integer> prededence = new HashMap<>();
        prededence.put("/", 2);
        prededence.put("*", 2);
        prededence.put("+", 1);
        prededence.put("-", 1);
        prededence.put("(", 0);

        Queue<String> queue = new LinkedList<>();
        Stack<String> stack = new Stack<>();

        for (String token : infixNotation) {
            if ("(".equals(token)) {
                stack.push(token);
                continue;
            }
            if (")".equals(token)) {
                while (!"(".equals(stack.peek())) {
                    queue.add(stack.pop());
                }
                stack.pop();
                continue;
            }
            if (prededence.containsKey(token)) {
                while (!stack.empty() && prededence.get(token) <= prededence.get(stack.peek())) {
                    queue.add(stack.pop());
                }
                stack.push(token);
                continue;
            }
            if (isNumber(token)) {
                queue.add(token);
                continue;
            }
            throw new IllegalArgumentException();
        }
        while (!stack.isEmpty()) {
            queue.add(stack.pop());
        }
        return queue;
    }

    /**
     * This fuction calculate all operators indexes
     *
     * @param statement math expression.
     * @return list of indexes that represent indexes of operators
     *
     * */
    private static List operatorIndexes(String statement){
        ArrayList<Integer> indexes = new ArrayList<>();
        for (int i = 0; i < statement.length(); i++) {
            if(isOperator(String.valueOf(statement.charAt(i)))){
                indexes.add(i);
            }
        }
        return indexes;
    }

    /**
     * This method parse input expression to tokens.
     *
     * @param statement math exression
     * @return list of tokens
     */
    private static ArrayList<String> buildTokens(String statement){
        statement = statement.replaceAll(" ","");
        List<Integer> indexes = operatorIndexes(statement);
        ArrayList<String> tokens = new ArrayList<>();
        int index = 0;
        for (int i = 0; i < indexes.size(); i++) {
            if(index == indexes.get(i)){
                tokens.add(String.valueOf(statement.charAt(indexes.get(i))));
                index = indexes.get(i) + 1;
                continue;
            }
            tokens.add(statement.substring(index,indexes.get(i)));
            tokens.add(String.valueOf(statement.charAt(indexes.get(i))));
            index = indexes.get(i) + 1;
        }
        if(!statement.substring(index,statement.length()).equals("")){
            tokens.add(statement.substring(index,statement.length()));
        }
        return tokens;
    }


    /**
     * Evaluate statement represented as string.
     *
     * @param statement mathematical statement containing digits, '.' (dot) as decimal mark,
     *                  parentheses, operations signs '+', '-', '*', '/'<br>
     *                  Example: <code>(1 + 38) * 4.5 - 1 / 2.</code>
     * @return string value containing result of evaluation or null if statement is invalid
     */
    public String evaluate(String statement){
        try {
            return tryEvaluate(statement);
        } catch (IllegalArgumentException | EmptyStackException e){
            return null;
        }
    }

    /**
     * This function evaluate postfix expression that came from math expression.
     *
     * @param statement math expression
     * @throws IllegalArgumentException,EmptyStackException if math expression is incorrect.
     * @return result of expression
     */
    public String tryEvaluate(String statement) {
        if(statement == null)
            throw new IllegalArgumentException();
        ArrayList<String> tokens = buildTokens(statement);
        ArrayList<String> postFix = new ArrayList<>(buildRPN(tokens));
        Stack<String> stack = new Stack<>();
        for (int i = 0; i < postFix.size(); i++)  {
            if(isNumber(postFix.get(i))){
                stack.push(postFix.get(i));
            } else {
                double first = Double.valueOf(stack.pop());
                double second = Double.valueOf(stack.pop());
                switch (postFix.get(i)){
                case "+":
                    stack.push(String.valueOf(first + second));
                    break;
                case "-":
                    stack.push(String.valueOf(second - first));
                    break;
                case "*":
                    stack.push(String.valueOf(first * second));
                    break;
                case "/":
                    if(first == 0)
                        return null;
                    stack.push(String.valueOf(second/first));
                    break;
                }
            }
        }
        double decimal = Double.parseDouble(stack.pop());
        if (decimal % 1 == 0){
            return String.valueOf(Math.round(decimal));
        }
        return String.valueOf(decimal);
    }

}
