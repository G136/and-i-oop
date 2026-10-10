package org.example;

/** I am the class that generalizes binary operations. It's what I am known for. */
public abstract class BinaryOperator extends Expression {

    public final Expression leftOperand;
    public final Expression rightOperand;
    public final String symbol;

    /**
     * Initialize an abstract binary operator with its operands and symbol representation.
     * @param leftOperand first expression, on the left
     * @param rightOperand second expression, on the right
     * @param symbol operator string representation
     */
    public BinaryOperator(Expression leftOperand, Expression rightOperand, String symbol) {
        this.leftOperand = leftOperand;
        this.rightOperand = rightOperand;
        this.symbol = symbol;
    }

    /**
     * Construct a known binary operator by matching against a given symbol.
     * @param left first operand passed to matched constructor
     * @param right second operand passed to matched constructor
     * @param symbol represents the binary operator
     * @return the match
     * @throws IllegalArgumentException when no match
     */
    public static BinaryOperator match(String symbol, Expression left, Expression right) {
        return switch (symbol) {
            case "+" -> new Add(left, right);
            case "-" -> new Sub(left, right);
            case "*" -> new Mul(left, right);
            case "/" -> new Div(left, right);
            default -> throw new IllegalArgumentException("unknown operator");
        };
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
