import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {

    private class Node {
        Item item;
        Node prev;
        Node next;
    }

    private Node first;
    private Node last;
    private int size;

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return size;
    }

    public void addFirst(Item item) {
        requireNotNull(item);
        var node = new Node();
        node.item = item;
        if (isEmpty()) {
            first = node;
            last = first;
        } else {
            var oldFirst = first;
            node.next = oldFirst;
            oldFirst.prev = node;
            first = node;
        }
        size++;
    }

    public void addLast(Item item) {
        requireNotNull(item);
        var node = new Node();
        node.item = item;
        if (isEmpty()) {
            first = node;
            last = first;
        } else {
            var oldLast = last;
            node.prev = oldLast;
            oldLast.next = node;
            last = node;
        }
        size++;
    }

    public Item removeFirst() {
        requireNotEmpty();
        size--;
        var oldFirst = first;
        if (size == 0) {
            first = null;
            last = null;
        } else if (size == 1) {
            first = oldFirst.next;
            first.prev = null;
            last = first;
        } else {
            first = oldFirst.next;
            first.prev = null;
        }
        return oldFirst.item;
    }

    public Item removeLast() {
        requireNotEmpty();
        size--;
        var oldLast = last;
        if (size == 0) {
            first = null;
            last = null;
        } else if (size == 1) {
            last = oldLast.prev;
            last.next = null;
            first = last;
        } else {
            last = oldLast.prev;
            last.next = null;
        }
        return oldLast.item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private void requireNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null.");
        }
    }

    private void requireNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("Deque is empty.");
        }
    }

    private class DequeIterator implements Iterator<Item> {

        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public Item next() {
            if (current == null) {
                throw new NoSuchElementException("Iterator is empty.");
            }
            var item = current.item;
            current = current.next;
            return item;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported.");
        }
    }

    public static void main(String[] args) {
        var deque = new Deque<Integer>();
        // [1]
        deque.addFirst(1);
        // []
        StdOut.println(deque.removeLast());
        // [3]
        deque.addFirst(3);
        // [4, 3]
        deque.addFirst(4);
        // [5, 4, 3]
        deque.addFirst(5);
        // [5, 4]
        StdOut.println(deque.removeLast());
        // [7, 5, 4]
        deque.addFirst(7);
        // [7, 5]
        StdOut.println(deque.removeLast());
    }
}
