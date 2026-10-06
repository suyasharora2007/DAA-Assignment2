package structures;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DynamicArrayTest {

    @Test
    void addAndGetShouldPreserveOrder() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(3, array.size());

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void addAtIndexShouldShiftElementsRight() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(30);

        array.add(1, 20);

        assertEquals(3, array.size());

        assertEquals(10, array.get(0));
        assertEquals(20, array.get(1));
        assertEquals(30, array.get(2));
    }

    @Test
    void removeShouldShiftElementsLeft() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(30);

        assertEquals(20, array.remove(1));

        assertEquals(2, array.size());

        assertEquals(10, array.get(0));
        assertEquals(30, array.get(1));
    }

    @Test
    void containsShouldFindValues() {

        DynamicArray array = new DynamicArray();

        array.add(10);
        array.add(20);
        array.add(20);

        assertTrue(array.contains(10));
        assertTrue(array.contains(20));

        assertFalse(array.contains(99));
    }

    @Test
    void shouldDoubleCapacityWhenFull() {

        DynamicArray array = new DynamicArray(2);

        array.add(1);
        array.add(2);

        assertEquals(2, array.capacity());

        array.add(3);

        assertEquals(4, array.capacity());

        assertEquals(1, array.get(0));
        assertEquals(2, array.get(1));
        assertEquals(3, array.get(2));
    }

    @Test
    void invalidElementIndexShouldThrowException() {

        DynamicArray array = new DynamicArray();

        array.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.get(1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.remove(1)
        );
    }

    @Test
    void invalidInsertPositionShouldThrowException() {

        DynamicArray array = new DynamicArray();

        array.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(-1, 5)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> array.add(2, 5)
        );
    }
}