package org.example;

public class Main {
    public static void main(String[] args) throws Exception {
        Expression expression = new Add( // (3+(2*x))
                new Number(3),
                new Mul(
                        new Number(2),
                        new Variable("x")
                )
        );

        System.out.println(expression);
        System.out.println(expression.derivative("x"));
        System.out.println(expression.eval("x = 10; y = 23"));

        Expression exprBase = new Add(new Number(5), new Number(6));
        Expression exprSame = new Add(new Number(5), new Number(6));
        Expression exprSwpd = new Add(new Number(6), new Number(5));
        System.out.println(exprBase.equals(exprSame));
        System.out.println(exprBase.equals(exprSwpd));
    }
}