package structures;

/**
 * Dynamic array that stores primitive int values.
 */
public class DynamicArray {

    private int[] data;
    private int size;

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
    }

    public int size() {
        return size;
    }

    public int capacity() {
        return data.length;
    }

    public void add(int x) {

        ensureCapacity();

        data[size] = x;
        size++;
    }

    public void add(int index, int x) {

        checkPositionIndex(index);

        ensureCapacity();

        for (int i = size; i > index; i--) {
            data[i] = data[i - 1];
        }

        data[index] = x;
        size++;
    }

    public int remove(int index) {

        checkElementIndex(index);

        int removed = data[index];

        for (int i = index; i < size - 1; i++) {
            data[i] = data[i + 1];
        }

        size--;

        return removed;
    }

    public int get(int index) {

        checkElementIndex(index);

        return data[index];
    }

    public boolean contains(int x) {

        for (int i = 0; i < size; i++) {

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