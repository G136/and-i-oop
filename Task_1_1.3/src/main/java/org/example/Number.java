package org.example;

/** I am the Number class. I hold constant value. */
public class Number extends Expression {

    public final int value;

    public Number(int value) {
        this.value = value;
    }

    @Override
    public int eval(String vars) {
        return this.value;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Number && this.value == ((Number) obj).value;
    }

    @Override
    public String toString() {
        return Integer.toString(value);
    }
}
