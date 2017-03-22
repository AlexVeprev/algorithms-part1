import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;

public class Deque<Item> implements Iterable<Item> {
    private Node first = null;
    private Node last = null;
    private int length = 0;

    private class Node {
        private Item value;
        private Node next;
        private Node prev;
    }

    public boolean isEmpty() {
        return length == 0;
    }

    public int size() {
        return length;
    }

    private void initSingleDeque(Item item) {
        first = new Node();
        first.value = item;
        first.prev = null;
        first.next = null;

        last = first;
    }

    public void addFirst(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        length++;

        if (first == null) {
            initSingleDeque(item);
            return;
        }

        Node newNode = new Node();
        newNode.next = first;
        first.prev = newNode;
        first = newNode;
        first.value = item;
    }

    public void addLast(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        length++;

        if (first == null) {
            initSingleDeque(item);
            return;
        }

        Node newNode = new Node();
        newNode.prev = last;
        last.next = newNode;
        last = newNode;
        last.value = item;
    }

    public Item removeFirst() {
        if (length == 0) {
            throw new NoSuchElementException();
        }

        length--;
        Item item = first.value;
        first = first.next;

        if (first != null) {
            first.prev = null;
        }
        else {
            last = null;
        }

        return item;
    }

    public Item removeLast() {
        if (length == 0) {
            throw new NoSuchElementException();
        }

        length--;
        Item item = last.value;
        last = last.prev;

        if (last != null) {
            last.next = null;
        }
        else {
            first = null;
        }

        return item;
    }

    @Override
    public Iterator<Item> iterator() {
        return new DequeIterator();
    }

    private class DequeIterator implements Iterator<Item>
    {
        private Node current = first;

        @Override
        public boolean hasNext() {
            return current != null;
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Item next()
        {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }

            Item item = current.value;
            current = current.next;
            return item;
        }
    }

    public static void main(String[] args) {
        // Some demo (for visual testing).

        Deque<Integer> d = new Deque<Integer>();
        int numOfElements = 6;

        // Fill demo deque.
        for (int i = 1; i <= numOfElements / 2; i++) {
            d.addFirst(i);
            d.addLast(i + 3);
        }

        StdOut.print("Current deque: ");
        for (Integer x : d) {
            StdOut.print(x + " ");
        }

        StdOut.print("\nRemove first (all): ");
        for (int i = 0; i < numOfElements; i++) {
            StdOut.print(d.removeFirst() + " ");
        }
        StdOut.print("\nIs empty now (true): " + d.isEmpty());

        StdOut.print("\nExpected exception: ");
        try {
            d.removeFirst();
            StdOut.print("failed :(");
        }
        catch (NoSuchElementException e) {
            StdOut.println(e.toString());
        }

        for (int i = 1; i <= numOfElements / 2; i++) {
            d.addFirst(i);
            d.addLast(i + 3);
        }

        StdOut.print("\nCurrent deque: ");
        for (Integer x : d) {
            StdOut.print(x + " ");
        }

        StdOut.print("\nRemove last (all): ");
        for (int i = 0; i < numOfElements; i++) {
            StdOut.print(d.removeLast() + " ");
        }
        StdOut.print("\nIs empty now (true): " + d.isEmpty());

        StdOut.print("\nExpected exception: ");
        try {
            d.removeLast();
            StdOut.print("failed :(");
        }
        catch (NoSuchElementException e) {
            StdOut.println(e.toString());
        }

    }
}
