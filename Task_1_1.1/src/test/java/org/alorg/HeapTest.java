package org.alorg;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

class HeapTest {
    void testSort(int arraySize) {
        int[] actual = new int[arraySize];

        Random random = new Random(67);
        for (int i = 0; i < arraySize; i++) {
            actual[i] = random.nextInt();
        }
        Heap.sort(actual);

        int[] expected = actual.clone();
        Arrays.sort(expected);

        assertArrayEquals(actual, expected);
    }

    @Test
    void sortEmpty() {
        testSort(0);
    }

    @Test
    void sortSingleton() {
        testSort(1);
    }

    @Test
    void sortPair() {
        testSort(2);
    }

    @Test
    void sort() {
        testSort(67);
    }

    @Test
    void sortInverted() {
        int[] actual = {7, 6, 5, 4, 3, 2, 1};
        Heap.sort(actual);

        int[] expected = actual.clone();
        Arrays.sort(expected);

        assertArrayEquals(actual, expected);
    }
}