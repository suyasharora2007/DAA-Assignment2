package structures;

import metrics.OperationCounter;

/**
 * Dynamic array that stores primitive int values.
 */
public class DynamicArray {

    private int[] data;
    private int size;

    private final OperationCounter counter;

    public DynamicArray() {
        this(4);
    }

    public DynamicArray(int initialCapacity) {
        if (initialCapacity < 1) {
            throw new IllegalArgumentException(
                    "Initial capacity must be positive"
            );
        }

        data = new int[initialCapacity];
        size = 0;
        counter = new OperationCounter();
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    public OperationCounter getCounter() {
        return counter;
    }

    public void resetCounter() {
        counter.reset();
    }

    public void add(int x) {

        ensureCapacity();

        data[size] = x;
        counter.move();

        size++;
    }

    public void add(int index, int x) {

        checkPositionIndex(index);

        ensureCapacity();

        for (int i = size; i > index; i--) {

            data[i] = data[i - 1];

            counter.step();
            counter.move();
        }

        data[index] = x;
        counter.move();

        size++;
    }

    public int remove(int index) {

        checkElementIndex(index);

        int removed = data[index];
        counter.step();

        for (int i = index; i < size - 1; i++) {

            data[i] = data[i + 1];

            counter.step();
            counter.move();
        }

        size--;

        return removed;
    }

    public int get(int index) {

        checkElementIndex(index);

        counter.step();

        return data[index];
    }

    public boolean contains(int x) {

        for (int i = 0; i < size; i++) {

            counter.step();

            counter.compare();

            if (data[i] == x) {
                return true;
            }
        }

        return false;
    }

    private void ensureCapacity() {

        if (size < data.length) {
            return;
        }

        int[] newData = new int[data.length * 2];

        for (int i = 0; i < size; i++) {

            newData[i] = data[i];

            counter.step();
            counter.move();
        }

        data = newData;
    }

    private void checkElementIndex(int index) {

        if (index < 0 || index >= size) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }

    private void checkPositionIndex(int index) {

        if (index < 0 || index > size) {

            throw new IndexOutOfBoundsException(
                    "Index: " + index + ", size: " + size
            );
        }
    }
}