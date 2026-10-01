package org.example;

public abstract class Expression {
    public abstract String toString();
    public abstract Expression derivative(String var);
    public abstract int eval(String vars) throws Exception;
    public abstract boolean equals(Expression e);
}
