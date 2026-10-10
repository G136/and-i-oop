package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class SubTest {

    @Test
    void eval() {
        assertEquals(0,
            new Sub(
                new Number(2),
                new Number(2)
            ).eval("")
        );

        assertEquals(-121,
            new Sub(
                new Number(2),
                new Variable("test")
            ).eval("test=123")
        );
    }

    @Test
    void derivative() {
        assertEquals(
            new Sub(
                new Number(1),
                new Number(1)
            ),
            new Sub(
                new Variable("test"),
                new Variable("test")
            ).derivative("test")
        );
    }

    @Test
    void equalsWorks() {
        assertNotEquals(
            new Sub(new Number(4), new Number(5)),
            new Sub(new Number(5), new Number(4))
        );
    }

    @Test
    void toStringWorks() {
        assertEquals("(3 - 3)",
            new Sub(
                new Number(3),
                new Number(3)
            ).toString());
    }
}