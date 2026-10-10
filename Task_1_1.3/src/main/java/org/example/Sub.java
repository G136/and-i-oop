package org.example;

/** I am the Sub class. I add things backwards. */
public class Sub extends BinaryOperator {

    public Sub(Expression leftOperand, Expression rightOperand) {
        super(leftOperand, rightOperand, "-");
    }

    @Override
    public int eval(String vars) {
        return this.leftOperand.eval(vars) - this.rightOperand.eval(vars);
    }

    @Override
    public Expression derivative(String var) {
        return new Sub(this.leftOperand.derivative(var), this.rightOperand.derivative(var));
    }
}
