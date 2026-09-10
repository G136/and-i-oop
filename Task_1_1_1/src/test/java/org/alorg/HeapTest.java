package org.alorg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {

    @Test
    void sort() {
        int[] actual = {7, 6, 5, 4, 3, 2, 1};
        Heap.sort(actual);

        int[] expected = actual.clone();
        Arrays.sort(expected);

        assertArrayEquals(actual, expected);
    }
}