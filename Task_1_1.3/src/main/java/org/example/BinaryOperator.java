package org.example;

/** I am the class that generalizes binary operations. It's what I am known for. */
public abstract class BinaryOperator extends Expression {

    public final Expression leftOperand;
    public final Expression rightOperand;
    public final String symbol;

    public BinaryOperator(Expression leftOperand, Expression rightOperand, String symbol) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.symbol = symbol;
    }

    @Override
    public boolean equals(Object obj) {
        return this.getClass() == obj.getClass()
            && this.leftOperand.equals(((BinaryOperator) obj).leftOperand)
            && this.rightOperand.equals(((BinaryOperator) obj).rightOperand);
    }

    @Override
    public String toString() {
        return "("
            + this.leftOperand.toString()
            + " " + symbol + " "
            + this.rightOperand.toString()
            + ")";
    }
}
