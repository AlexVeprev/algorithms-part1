import java.util.Iterator;
import java.util.NoSuchElementException;

import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomizedQueue<Item> implements Iterable<Item> {
    private int last = -1;
    private Item[] array;

    public RandomizedQueue() {
        array = (Item[]) new Object[1];
    }

    public boolean isEmpty() {
        return size() == 0;
    }

    public int size() {
        return last + 1;
    }

    private void resize(int size) {
        Item[] newArray = (Item[]) new Object[size];

        for (int i = 0; i < size(); i++) {
            newArray[i] = array[i];
        }

        array = newArray;
    }

    public void enqueue(Item item) {
        if (item == null) {
            throw new NullPointerException();
        }

        if (size() == array.length) {
            resize(array.length * 2);
        }

        array[++last] = item;
    }

    public Item dequeue() {
        if (last < 0) {
            throw new NoSuchElementException();
        }

        if (size() < array.length / 4) {
            resize(array.length / 2);
        }

        int deqIx = StdRandom.uniform(size());
        exch(array, deqIx, last);

        return array[last--];
    }

    public Item sample() {
        if (last < 0) {
            throw new NoSuchElementException();
        }

        return array[StdRandom.uniform(size())];
    }

    private void exch(Item[] arr, int ix1, int ix2) {
        Item swap = arr[ix1];
        arr[ix1] = arr[ix2];
        arr[ix2] = swap;
    }

    @Override
    public Iterator<Item> iterator() {
        return new RandQueueIterator();
    }

    private class RandQueueIterator implements Iterator<Item>
    {
        private int lastUnhandled = last;
        private int returnIx;
        private Item[] iterArray = array.clone();

        @Override
        public boolean hasNext() {
            return lastUnhandled >= 0;
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

            returnIx = StdRandom.uniform(lastUnhandled + 1);
            exch(iterArray, returnIx, lastUnhandled);

            return iterArray[lastUnhandled--];
        }
    }

    public static void main(String[] args) {
        // Some demo (for visual testing).

        RandomizedQueue<String> rqString = new RandomizedQueue<String>();
        RandomizedQueue<Integer> rqInt = new RandomizedQueue<Integer>();
        int numOfElements = 5;

        // Fill demo randomized queues.
        for (int i = 0; i < numOfElements; i++) {
            rqString.enqueue("x" + Integer.toString(i));
            rqInt.enqueue(i);
        }

        // Print samples.
        for (int i = 0; i < numOfElements; i++) {
            StdOut.println("Sample " + (i + 1) + ": string=" + rqString.sample() + " | int=" + rqInt.sample());
        }

        // Plane iterators.
        for (int i = 0; i < numOfElements; i++) {
            StdOut.print("Iterator " + (i + 1) + ": string ");

            for (String x : rqString) {
                StdOut.print(x + " ");
            }

            StdOut.print("| int ");

            for (Integer x : rqInt) {
                StdOut.print(x + " ");
            }
            StdOut.println();
        }

        // Nested iterators.
        StringBuilder inner = new StringBuilder();
        StringBuilder outer = new StringBuilder();
        for (String x : rqString) {
            for (String y : rqString) {
                inner.append(y + " ");
            }
            inner.append("| ");
            outer.append(x + " ");
        }
        StdOut.println("Nested iterators (string):");
        StdOut.println(" inner: " + inner);
        StdOut.println(" outer: " + outer);

        inner = new StringBuilder();
        outer = new StringBuilder();
        for (Integer x : rqInt) {
            for (Integer y : rqInt) {
                inner.append(y + " ");
            }
            inner.append("| ");
            outer.append(x + " ");
        }
        StdOut.println("Nested iterators (int):");
        StdOut.println(" inner: " + inner);
        StdOut.println(" outer: " + outer);

        // Dequeue.
        for (int i = 0; i < numOfElements; i++) {
            StdOut.println("Is empty " + (i + 1) + ": string=" + rqString.isEmpty() + " | int=" + rqInt.isEmpty());
            StdOut.println("Dequeue  " + (i + 1) + ": string=" + rqString.dequeue() + "     | int=" + rqInt.dequeue());
        }

        StdOut.println("Is empty (expect \"true\"): string=" + rqString.isEmpty() + " | int=" + rqInt.isEmpty());

        try {
            rqString.dequeue();
            StdOut.println("Unexpected line!");
        } catch (NoSuchElementException e) {
            StdOut.println("Expected exception " + e.toString());
        }

        try {
            rqInt.dequeue();
            StdOut.println("Unexpected line!");
        } catch (NoSuchElementException e) {
            StdOut.println("Expected exception " + e.toString());
        }

        RandomizedQueue<String> rq = new RandomizedQueue<String>();

        for (int i = 0; i < 10; i++) {
            rq.enqueue("A");
            rq.enqueue("B");
            rq.enqueue("C");
            rq.enqueue("D");
            StdOut.print(rq.dequeue());
            StdOut.print(rq.dequeue());
            StdOut.print(rq.dequeue());
            StdOut.print(rq.dequeue() + "\n");
        }
    }

}
