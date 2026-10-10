package org.example;

/** I am the Add class. I... add things. */
public class Add extends BinaryOperator {

    public Add(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand, "+");
    }

    @Override
    public int eval(String vars) {
        return this.leftOperand.eval(vars) + this.rightOperand.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Add(this.leftOperand.derivative(var), this.rightOperand.derivative(var));
    }
}
