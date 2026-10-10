package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class DivTest {

    @Test
    void eval() {
        assertEquals(1,
            new Div(
                new Number(2),
                new Number(2)
            ).eval("")
        );

        assertEquals(3,
            new Div(
                new Number(10),
                new Variable("test")
            ).eval("test=3")
        );
    }

    @Test
    void derivative() {
        assertEquals(
            new Div(
                new Sub(
                    new Mul(
                        new Number(1),
                        new Variable("test")
                    ),
                    new Mul(
                        new Variable("test"),
                        new Number(1)
                    )
                ),
                new Mul(
                    new Variable("test"),
                    new Variable("test")
                )
            ),
            new Div(
                new Variable("test"),
                new Variable("test")
            ).derivative("test")
        );
    }

    @Test
    void equalsWorks() {
        assertNotEquals(
            new Div(new Number(4), new Number(2)),
            new Div(new Number(2), new Number(4))
        );
    }

    @Test
    void toStringWorks() {
        assertEquals("(3 / 3)",
        new Div(
            new Number(3),
            new Number(3)
        ).toString());
    }
}