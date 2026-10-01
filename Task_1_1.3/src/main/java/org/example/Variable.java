package org.example;

import java.util.SplittableRandom;

public class Variable extends Expression {
    public final String symbol;

    public Variable(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public String toString() {
        return this.symbol;
    }

    @Override
    public Expression derivative(String var) {
        return new Number(var.equals(symbol) ? 1 : 0);
    }

    @Override
    public int eval(String vars) throws Exception {
        for (String var : vars.split(";")) {
            String[] symbol_value = var.split("=");
            String symbol = symbol_value[0].strip();
            int value = Integer.parseInt(symbol_value[1].strip());

            if (symbol.equals(this.symbol)) {
                return value;
            }
        }
        throw new Exception(this.symbol + " not present in " + vars);
    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof Variable && ((Variable) e).symbol.equals(this.symbol);
    }
}
