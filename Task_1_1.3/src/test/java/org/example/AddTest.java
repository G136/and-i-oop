package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class AddTest {

    @Test
    void eval() {
        assertEquals(4,
            new Add(
                new Number(2),
                new Number(2)
            ).eval("")
        );

        assertEquals(125,
            new Add(
                new Number(2),
                new Variable("test")
            ).eval("test=123")
        );
    }

    @Test
    void derivative() {
        assertEquals(
            new Add(
                new Number(1),
                new Number(1)
            ),
            new Add(
                new Variable("test"),
                new Variable("test")
            ).derivative("test")
        );
    }

    @Test
    void equalsWorks() {
        assertNotEquals(
            new Add(new Number(4), new Number(5)),
            new Add(new Number(5), new Number(4))
        );
    }

    @Test
    void toStringWorks() {
        assertEquals("(3 + 3)",
        new Add(
            new Number(3),
            new Number(3)
        ).toString());
    }
}