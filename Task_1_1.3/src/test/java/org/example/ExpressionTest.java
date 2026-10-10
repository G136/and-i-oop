package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class ExpressionTest {

    @Test
    void parse() {
        assertEquals(
            new Add(new Number(3), new Mul(new Number(2), new Variable("x"))),
            Expression.parse("(3 + (2 * x))")
        );

        assertEquals(
            new Div(new Variable(":"), new Sub(new Number(6), new Number(7))),
            Expression.parse("(: / (6 - 7))")
        );

        assertEquals(
            new Number(110),
            Expression.parse("(  110")
        );
    }
}