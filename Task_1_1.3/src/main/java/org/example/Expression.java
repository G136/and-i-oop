package org.example;

/** All expressions start somewhere, and the place is here. */
public abstract class Expression {

    /**
     * Parse given string as an expression.
     * @param expr given string, binary operations must be surrounded by parentheses
     * @return parsed expression
     */
    public static Expression parse(String expr) {
        for (int parencount = 0, i = 0; i < expr.length(); i++) {
            switch (expr.charAt(i)) {
                case '(':
                    parencount++;
                    break;
                case ')':
                    parencount--;
                    break;
                case '+', '*':
                    if (parencount == 1) {
                        return BinaryOperator.match(
                            String.valueOf(expr.charAt(i)),
                            parse(expr.substring(0, i)),
                            parse(expr.substring(i + 1))
                        );
                    }
                default:
                    // uhh all good 👍
            }
        }

        expr = expr.replaceAll("[() ]", "");

        try {
            return new Number(Integer.parseInt(expr));
        } catch (NumberFormatException e) {
            return new Variable(expr);
        }
    }

    /**
     * Evaluate the expression.
     * @param vars ;-separated string of values to be assigned to variables in the form var=val
     * @return the result of the evaluation
     */
    public abstract int eval(String vars);

    /**
     * Take a derivative.
     * @param var the variable symbol this derivative will be taken with respect to
     * @return a new expression representing this one's derivative
     */
    public abstract Expression derivative(String var);

    @Override
    public abstract boolean equals(Object obj); // note: not overriding hashCode, just this

    @Override
    public abstract String toString();
}
