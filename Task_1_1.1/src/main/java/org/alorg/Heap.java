package org.alorg;

/** Just a few heap things; the .sort method is the one you care about. */
public class Heap {
    /**
     * Make an array of integers have the heap form (in-place).
     * @param nums this will become heap
     */
    private static void toHeap(int[] nums) {
        for (int i = nums.length / 2; i >= 0; i--) {
            siftDown(nums, i, nums.length);
        }
    }

    /**
     * Sift down number at index of heap. Greater numbers float up (see the "vvv"s). In-place.
     * @param heap the heap
     * @param at the at index
     * @param till don't sift past this heap index
     */
    private static void siftDown(int[] heap, int at, int till) {
        assert (0 <= at && at <= till && till <= heap.length);

        int l = 2 * at + 1;
        int r = 2 * at + 2;

        //                     vvv
        if (l < till && heap[l] > heap[at]) {
            int top = heap[l];

            heap[l] = heap[at];
            heap[at] = top;

            siftDown(heap, l, till);
        }
        //                     vvv
        if (r < till && heap[r] > heap[at]) {
            int top = heap[r];

            heap[r] = heap[at];
            heap[at] = top;

            siftDown(heap, r, till);
        }
    }

    /**
     * Heap sort the numbers (In-place).
     * @param nums the numbers
     */
    public static void sort(int[] nums) {
        toHeap(nums);

        for (int till = nums.length - 1; till >= 0; till--) {
            int top = nums[0];

            nums[0] = nums[till];
            nums[till] = top;

            siftDown(nums, 0, till);
        }
    }
}
