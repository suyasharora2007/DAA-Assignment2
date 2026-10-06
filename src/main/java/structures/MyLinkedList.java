package structures;

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

    public int size() {
        return size;
    }

    public void add(int x) {

        Node newNode = new Node(x);

        if (head == null) {
            head = newNode;
        } else {
            Node current = head;

            while (current.next != null) {
                current = current.next;
            }

            current.next = newNode;
        }

        size++;
    }

    public void add(int index, int x) {

        checkPositionIndex(index);

        Node newNode = new Node(x);

        if (index == 0) {

            newNode.next = head;
            head = newNode;

        } else {

            Node previous = getNode(index - 1);

            newNode.next = previous.next;
            previous.next = newNode;
        }

        size++;
    }

    public int remove(int index) {

        checkElementIndex(index);

        int removed;

        if (index == 0) {

            removed = head.data;
            head = head.next;

        } else {

            Node previous = getNode(index - 1);
            Node target = previous.next;

            removed = target.data;
            previous.next = target.next;
        }

        size--;

        return removed;
    }

    public int get(int index) {

        checkElementIndex(index);

        return getNode(index).data;
    }

    public boolean contains(int x) {

        Node current = head;

        while (current != null) {

            if (current.data == x) {
                return true;
            }

            current = current.next;
        }

        return false;
    }

    private Node getNode(int index) {

        Node current = head;

        for (int i = 0; i < index; i++) {
            current = current.next;
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