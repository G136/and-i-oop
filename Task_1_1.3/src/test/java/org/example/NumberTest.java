package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class NumberTest {

    @Test
    void eval() {
        for (int i = -1000; i < 1000; i++) {
            assertEquals(i, new Number(i).eval("bologna"));
        }
    }

    @Test
    void derivative() {
        for (int i = -1000; i < 1000; i++) {
            assertEquals(new Number(0), new Number(i).derivative("bologna"));
        }
    }

    @Test
    void equalsWorks() {
        for (int i = -1000; i < 1000; i++) {
            assertEquals(new Number(i), new Number(i));
            if (i != 0) {
                assertNotEquals(new Number(i), new Number(-i));
            } else {
                assertEquals(new Number(i), new Number(-i));
            }
        }
    }

    @Test
    void toStringWorks() {
        for (int i = -1000; i < 1000; i++) {
            assertEquals(Integer.toString(i), new Number(i).toString());
        }
    }
}