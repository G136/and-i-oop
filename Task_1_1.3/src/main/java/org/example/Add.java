package org.example;

public class Add extends Expression {
    public final Expression l, r;

    public Add(Expression l, Expression r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public String toString() {
        return "(" + this.l.toString() + " + " + this.r.toString() + ")";
    }

    @Override
    public Expression derivative(String var) {
        return new Add(this.l.derivative(var), this.r.derivative(var));
    }

    @Override
    public int eval(String vars) throws Exception {
        return this.l.eval(vars) + this.r.eval(vars);
    }

    @Override
    public boolean equals(Expression e) {
        return e instanceof Add && this.l.equals(((Add) e).l) && this.r.equals(((Add) e).r);
    }
}
