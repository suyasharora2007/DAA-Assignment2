package structures;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

class MyLinkedListTest {

    @Test
    void addAndGetShouldPreserveOrder() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(3, list.size());

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtIndexShouldInsertCorrectly() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(30);

        list.add(1, 20);

        assertEquals(3, list.size());

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void addAtHeadShouldWork() {

        MyLinkedList list = new MyLinkedList();

        list.add(20);
        list.add(30);

        list.add(0, 10);

        assertEquals(3, list.size());

        assertEquals(10, list.get(0));
        assertEquals(20, list.get(1));
        assertEquals(30, list.get(2));
    }

    @Test
    void removeShouldReturnRemovedValue() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(30);

        assertEquals(20, list.remove(1));

        assertEquals(2, list.size());

        assertEquals(10, list.get(0));
        assertEquals(30, list.get(1));
    }

    @Test
    void removeHeadShouldWork() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);

        assertEquals(10, list.remove(0));

        assertEquals(1, list.size());
        assertEquals(20, list.get(0));
    }

    @Test
    void containsShouldFindValues() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(20);
        list.add(20);

        assertTrue(list.contains(10));
        assertTrue(list.contains(20));

        assertFalse(list.contains(99));
    }

    @Test
    void duplicateValuesShouldWork() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);
        list.add(10);
        list.add(10);

        assertEquals(3, list.size());

        assertEquals(10, list.get(0));
        assertEquals(10, list.get(1));
        assertEquals(10, list.get(2));
    }

    @Test
    void invalidGetShouldThrowException() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(-1)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.get(1)
        );
    }

    @Test
    void invalidRemoveShouldThrowException() {

        MyLinkedList list = new MyLinkedList();

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.remove(0)
        );
    }

    @Test
    void invalidInsertShouldThrowException() {

        MyLinkedList list = new MyLinkedList();

        list.add(10);

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(-1, 5)
        );

        assertThrows(
                IndexOutOfBoundsException.class,
                () -> list.add(2, 5)
        );
    }

    @Test
    void emptyListShouldWork() {

        MyLinkedList list = new MyLinkedList();

        assertEquals(0, list.size());
        assertFalse(list.contains(10));
    }
}