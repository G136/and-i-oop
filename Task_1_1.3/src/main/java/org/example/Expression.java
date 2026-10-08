package org.example;

/** All expressions start somewhere, and the place is here. */
public abstract class Expression {

    public abstract int eval(String vars);

    public abstract Expression derivative(String var);

    @Override
    public abstract boolean equals(Object obj); // note: not overriding hashCode, just this

    @Override
    public abstract String toString();
}
