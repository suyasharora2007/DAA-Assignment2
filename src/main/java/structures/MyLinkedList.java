package structures;

import metrics.OperationCounter;

/**
 * Singly linked list storing primitive int values.
 */
public class MyLinkedList {

    private static class Node {

        int data;
        Node next;

        Node(int data) {
            this.data = data;
        }
    }

    private Node head;
    private int size;

    private final OperationCounter counter;

    public MyLinkedList() {
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

    public void add(int x) {

        Node newNode = new Node(x);

        if (head == null) {

            head = newNode;
            counter.move();

        } else {

            Node current = head;

            while (current.next != null) {

                current = current.next;

                counter.step();
            }

            current.next = newNode;

            counter.move();
        }

        size++;
    }

    public void add(int index, int x) {

        checkPositionIndex(index);

        Node newNode = new Node(x);

        if (index == 0) {

            newNode.next = head;
            head = newNode;

            counter.move();

        } else {

            Node previous = getNode(index - 1);

            newNode.next = previous.next;
            previous.next = newNode;

            counter.move();
        }

        size++;
    }

    public int remove(int index) {

        checkElementIndex(index);

        int removed;

        if (index == 0) {

            removed = head.data;

            head = head.next;

            counter.move();

        } else {

            Node previous = getNode(index - 1);
            Node target = previous.next;

            removed = target.data;

            previous.next = target.next;

            counter.move();
        }

        size--;

        return removed;
    }

    public int get(int index) {

        checkElementIndex(index);

        Node node = getNode(index);

        return node.data;
    }

    public boolean contains(int x) {

        Node current = head;

        while (current != null) {

            counter.compare();

            if (current.data == x) {
                return true;
            }

            current = current.next;

            counter.step();
        }

        return false;
    }

    private Node getNode(int index) {

        Node current = head;

        for (int i = 0; i < index; i++) {

            current = current.next;

            counter.step();
        }

        return current;
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