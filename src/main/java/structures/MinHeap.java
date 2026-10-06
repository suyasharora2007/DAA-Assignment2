package structures;

import metrics.OperationCounter;

/**
 * Array-based min heap storing primitive int values.
 */
public class MinHeap {

    private int[] heap;
    private int size;

    private final OperationCounter counter;

    public MinHeap() {
        this(16);
    }

    public MinHeap(int initialCapacity) {

        if (initialCapacity < 1) {
            throw new IllegalArgumentException(
                    "Initial capacity must be positive"
            );
        }

        heap = new int[initialCapacity];
        counter = new OperationCounter();
    }

    public int size() {
        return size;
    }

    public OperationCounter getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter.reset();
    }

    public void insert(int x) {

        ensureCapacity();

        heap[size] = x;
        counter.move();

        bubbleUp(size);

        size++;
    }

    public int peekMin() {

        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        counter.step();

        return heap[0];
    }

    public int extractMin() {

        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        counter.step();

        int minimum = heap[0];

        size--;

        if (size > 0) {

            heap[0] = heap[size];

            counter.step();
            counter.move();

            bubbleDown(0);
        }

        return minimum;
    }

    private void bubbleUp(int index) {

        while (index > 0) {

            int parent = (index - 1) / 2;

            counter.step();
            counter.step();

            counter.compare();

            if (heap[parent] <= heap[index]) {
                break;
            }

            swap(parent, index);

            index = parent;
        }
    }

    private void bubbleDown(int index) {

        while (true) {

            int left = 2 * index + 1;
            int right = 2 * index + 2;

            int smallest = index;

            if (left < size) {

                counter.step();
                counter.step();

                counter.compare();

                if (heap[left] < heap[smallest]) {
                    smallest = left;
                }
            }

            if (right < size) {

                counter.step();
                counter.step();

                counter.compare();

                if (heap[right] < heap[smallest]) {
                    smallest = right;
                }
            }

            if (smallest == index) {
                break;
            }

            swap(index, smallest);

            index = smallest;
        }
    }

    private void swap(int first, int second) {

        int temp = heap[first];

        heap[first] = heap[second];

        heap[second] = temp;

        counter.move();
        counter.move();
        counter.move();
    }

    private void ensureCapacity() {

        if (size < heap.length) {
            return;
        }

        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {

            newHeap[i] = heap[i];

            counter.step();
            counter.move();
        }

        heap = newHeap;
    }
}