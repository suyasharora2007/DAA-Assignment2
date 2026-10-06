package structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class OperationCounterTest {

    @Test
    void dynamicArrayShouldCountOperations() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);

        array.resetCounter();

        array.get(0);

        assertEquals(1, array.getCounter().getSteps());
    }

    @Test
    void dynamicArrayContainsShouldCountComparison() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        array.resetCounter();

        assertTrue(array.contains(20));

        assertEquals(
                2,
                array.getCounter().getComparisons()
        );
    }

    @Test
    void linkedListGetShouldCountTraversalSteps() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        list.resetCounter();

        assertEquals(30, list.get(2));

        assertEquals(
                2,
                list.getCounter().getSteps()
        );
    }

    @Test
    void heapInsertShouldCountOperations() {

        MinHeap heap = new MinHeap();

        heap.insert(30);
        heap.insert(10);
        heap.insert(20);

        assertTrue(
                heap.getCounter().getMoves() > 0
        );

        assertTrue(
                heap.getCounter().getComparisons() > 0
        );
    }
}