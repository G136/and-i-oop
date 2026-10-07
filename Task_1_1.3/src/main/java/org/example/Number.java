package org.example;

public class Number extends Expression{
    public final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public int eval(String vars) {
        return this.value;
    }

    @Override
    public boolean equals(Object e) {
        return e instanceof Number && this.value == ((Number) e).value;
    }
}
