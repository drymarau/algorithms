import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {

    private Item[] items;
    private int length;

    public RandomizedQueue() {
        // noinspection unchecked
        this.items = (Item[]) new Object[1];
        this.length = 0;
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return length;
    }

    public void enqueue(Item item) {
        requireNotNull(item);
        if (length == items.length) {
            resize(items.length * 2);
        }
        items[length++] = item;
    }

    public Item dequeue() {
        requireNotEmpty();
        var i = StdRandom.uniformInt(length);
        var item = items[i];
        length--;
        if (i == length) {
            items[i] = null;
        } else {
            items[i] = items[length];
            items[length] = null;
        }
        if (length > 0 && length == items.length / 4) {
            resize(items.length / 2);
        }
        return item;
    }

    public Item sample() {
        requireNotEmpty();
        return items[StdRandom.uniformInt(length)];
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandomizedQueueIterator();
    }

    private void requireNotNull(Item item) {
        if (item == null) {
            throw new IllegalArgumentException("item is null.");
        }
    }

    private void requireNotEmpty() {
        if (isEmpty()) {
            throw new NoSuchElementException("RandomizedQueue is empty.");
        }
    }

    private void resize(int capacity) {
        // noinspection unchecked
        var copy = (Item[]) new Object[capacity];
        if (length >= 0) {
            System.arraycopy(items, 0, copy, 0, length);
        }
        items = copy;
    }

    private class RandomizedQueueIterator implements Iterator<Item> {

        private final Item[] items;
        private int i;

        RandomizedQueueIterator() {
            // noinspection unchecked
            this.items = (Item[]) new Object[length];
            System.arraycopy(RandomizedQueue.this.items, 0, this.items, 0, length);
            StdRandom.shuffle(this.items);
        }

        @Override
        public boolean hasNext() {
            return i < items.length;
        }

        @Override
        public Item next() {
            if (!hasNext()) {
                throw new NoSuchElementException("Iterator is empty.");
            }
            return items[i++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("remove() is not supported.");
        }
    }

    public static void main(String[] args) {
        var queue = new RandomizedQueue<Integer>();
        for (int i = 0; i < 5; i++) {
            queue.enqueue(i);
        }
        for (var item : queue) {
            StdOut.println(item);
        }
        while (!queue.isEmpty()) {
            StdOut.println(queue.size());
            StdOut.println(queue.sample());
            StdOut.println(queue.dequeue());
        }
    }
}
