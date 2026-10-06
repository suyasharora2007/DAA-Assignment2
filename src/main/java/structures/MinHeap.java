package structures;

/**
 * Array-based min heap storing primitive int values.
 */
public class MinHeap {

    private int[] heap;
    private int size;

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
    }

    public int size() {
        return size;
    }

    public void insert(int x) {

        ensureCapacity();

        heap[size] = x;

        bubbleUp(size);

        size++;
    }

    public int peekMin() {

        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        return heap[0];
    }

    public int extractMin() {

        if (size == 0) {
            throw new IllegalStateException("Heap is empty");
        }

        int minimum = heap[0];

        size--;

        if (size > 0) {
            heap[0] = heap[size];
            bubbleDown(0);
        }

        return minimum;
    }

    private void bubbleUp(int index) {

        while (index > 0) {

            int parent = (index - 1) / 2;

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

            if (left < size && heap[left] < heap[smallest]) {
                smallest = left;
            }

            if (right < size && heap[right] < heap[smallest]) {
                smallest = right;
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
    }

    private void ensureCapacity() {

        if (size < heap.length) {
            return;
        }

        int[] newHeap = new int[heap.length * 2];

        for (int i = 0; i < size; i++) {
            newHeap[i] = heap[i];
        }

        heap = newHeap;
    }
}