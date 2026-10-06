package structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MinHeapTest {

    @Test
    void insertAndPeekShouldReturnMinimum() {

        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertEquals(10, heap.peekMin());
        assertEquals(3, heap.size());
    }

    @Test
    void extractMinShouldReturnValuesInSortedOrder() {

        MinHeap heap = new MinHeap();

        heap.insert(50);
        heap.insert(10);
        heap.insert(40);
        heap.insert(20);
        heap.insert(30);

        assertEquals(10, heap.extractMin());
        assertEquals(20, heap.extractMin());
        assertEquals(30, heap.extractMin());
        assertEquals(40, heap.extractMin());
        assertEquals(50, heap.extractMin());

        assertEquals(0, heap.size());
    }

    @Test
    void duplicateValuesShouldWork() {

        MinHeap heap = new MinHeap();

        heap.insert(10);
        heap.insert(10);
        heap.insert(5);

        assertEquals(5, heap.extractMin());
        assertEquals(10, heap.extractMin());
        assertEquals(10, heap.extractMin());
    }

    @Test
    void singleElementHeapShouldWork() {

        MinHeap heap = new MinHeap();

        heap.insert(42);

        assertEquals(42, heap.peekMin());
        assertEquals(42, heap.extractMin());
        assertEquals(0, heap.size());
    }

    @Test
    void emptyPeekShouldThrowException() {

        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::peekMin
        );
    }

    @Test
    void emptyExtractShouldThrowException() {

        MinHeap heap = new MinHeap();

        assertThrows(
                IllegalStateException.class,
                heap::extractMin
        );
    }

    @Test
    void randomValuesShouldExtractInNonDecreasingOrder() {

        MinHeap heap = new MinHeap();

        int[] values = {
                17, 3, 25, 1, 9, 12, 7, 30, 2, 15
        };

        for (int value : values) {
            heap.insert(value);
        }

        int previous = Integer.MIN_VALUE;

        while (heap.size() > 0) {

            int current = heap.extractMin();

            assertTrue(current >= previous);

            previous = current;
        }
    }
}