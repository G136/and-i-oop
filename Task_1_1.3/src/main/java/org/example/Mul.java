package org.example;

/** I am the Mul class. I multiply. */
public class Mul extends BinaryOperator {

    public Mul(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand, "*");
    }

    @Override
    public int eval(String vars) {
        return this.leftOperand.eval(vars) * this.rightOperand.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(
            new Mul(this.leftOperand.derivative(var), this.rightOperand),
            new Mul(this.leftOperand, this.rightOperand.derivative(var))
        );
    }
}
