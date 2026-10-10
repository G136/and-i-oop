package org.example;

/** I am the Variable class. Value may be dynamically impressed upon me. */
public class Variable extends Expression {

    public final String symbol;

    public Variable(String symbol) {
        this.symbol = symbol;
    }

    @Override
    public int eval(String vars) {
        for (String var : vars.split(";")) {
            String[] symbolValuePair = var.split("=");
            String symbol = symbolValuePair[0].strip();
            int value = Integer.parseInt(symbolValuePair[1].strip());

            if (symbol.equals(this.symbol)) {
                return value;
            }
        }
        throw new IllegalArgumentException(this.symbol + " not present in " + vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Number(var.equals(symbol) ? 1 : 0);
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof Variable && ((Variable) obj).symbol.equals(this.symbol);
    }

    @Override
    public String toString() {
        return this.symbol;
    }
}
