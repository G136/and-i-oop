package org.example;

public class Mul extends Expression {
    public final Expression l, r;

    public Mul(Expression l, Expression r) {
        this.l = l;
        this.r = r;
    }

    @Override
    public String toString() {
        return "(" + this.l.toString() + " * " + this.r.toString() + ")";
    }

    @Override
    public Expression derivative(String var) {
        return new Add(
                new Mul(this.l.derivative(var), this.r),
                new Mul(this.l, this.r.derivative(var))
        );
    }

    @Override
    public int eval(String vars) throws Exception {
        return this.l.eval(vars) * this.r.eval(vars);
    }

    @Override
    public boolean equals(Object e) {
        return e instanceof Mul && this.l.equals(((Mul) e).l) && this.r.equals(((Mul) e).r);
    }
}
