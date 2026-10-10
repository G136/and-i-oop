package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class MulTest {

    @Test
    void eval() {
        assertEquals(4,
            new Mul(
                new Number(2),
                new Number(2)
            ).eval("")
        );

        assertEquals(25,
            new Mul(
                new Number(-5),
                new Variable("test")
            ).eval("test=-5")
        );
    }

    @Test
    void derivative() {
        assertEquals(
            new Add(
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
            ).derivative("test")
        );
    }

    @Test
    void equalsWorks() {
        assertNotEquals(
            new Mul(new Number(4), new Number(5)),
            new Mul(new Number(5), new Number(4))
        );
    }

    @Test
    void toStringWorks() {
        assertEquals("(3 * 3)",
        new Mul(
            new Number(3),
            new Number(3)
        ).toString());
    }
}