package org.example;

/** I am the Div class. I divvy up. */
public class Div extends BinaryOperator {

    public Div(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand, "/");
    }

    @Override
    public int eval(String vars) {
        return this.leftOperand.eval(vars) / this.rightOperand.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Div(
            new Sub(
                new Mul(this.leftOperand.derivative(var), this.rightOperand),
                new Mul(this.leftOperand, this.rightOperand.derivative(var))
            ),
            new Mul(this.rightOperand, this.rightOperand)
        );
    }
}
