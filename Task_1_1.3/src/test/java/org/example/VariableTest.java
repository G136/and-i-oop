package org.example;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class VariableTest {

    @Test
    void eval() {
        assertEquals(1, new Variable("a").eval("a = 1"));
        assertEquals(2, new Variable("bebebe").eval("bebebe=2"));
        assertEquals(4, new Variable("3").eval("3=4"));
    }

    @Test
    void derivative() {
        assertEquals(new Number(1), new Variable("a").derivative("a"));
        assertEquals(new Number(0), new Variable("bebebe").derivative("a"));
    }

    @Test
    void equalsWorks() {
        assertEquals(new Variable("a"), new Variable("a"));
        assertNotEquals(new Variable("bababa"), new Variable("bebebe"));
        assertNotEquals(new Variable("a"), new Variable("A"));
        assertNotEquals(new Variable("1"), new Variable("01"));
    }

    @Test
    void toStringWorks() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 1000; i++) {
            stringBuilder.append("a");
            assertEquals(
                stringBuilder.toString(),
                new Variable(stringBuilder.toString()).toString()
            );
        }
    }
}