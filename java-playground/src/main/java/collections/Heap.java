package collections;

import java.util.Arrays;
import java.util.Comparator;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
class Heap<E> {

    private static final int MIN_SIZE = 10;
    private final Comparator<E> comparator;

    @SuppressWarnings("unchecked")
    private E[] array = (E[]) new Object[MIN_SIZE];

    private int currentSize = 0;

    public boolean isEmpty() {
        return currentSize == 0;
    }

    public void push(E e) {
        if (currentSize == array.length) {
            resize(currentSize * 2);
        }
        array[currentSize] = e;
        currentSize++;
        bubbleUp(currentSize - 1);
    }

    private void bubbleUp(int index) {
        if (index == 0) {
            return;
        }
        int parent = index / 2;
        if (comparator.compare(array[parent], array[index]) > 0) {
            swap(parent, index);
            bubbleUp(parent);
        }
    }

    public E pop() {
        if (currentSize == 0) {
            throw new NoSuchElementException();
        }
        E e = array[0];
        currentSize--;
        array[0] = array[currentSize];
        array[currentSize] = null;
        bubbleDown(0);
        if (array.length > MIN_SIZE && 2 * currentSize > array.length) {
            resize(currentSize * 2);
        }
        return e;
    }


    private void bubbleDown(int index) {
        int leftChild = index * 2;
        if (leftChild >= currentSize) {
            return;
        }
        int rightChild = leftChild + 1;
        int minIndex;
        if (rightChild >= currentSize || comparator.compare(array[leftChild], array[rightChild]) < 0) {
            minIndex = leftChild;
        } else {
            minIndex = rightChild;
        }
        if (comparator.compare(array[index], array[minIndex]) > 0) {
            swap(index, minIndex);
            bubbleDown(minIndex);
        }
    }

    private void swap(int a, int b) {
        E tmp = array[a];
        array[a] = array[b];
        array[b] = tmp;
    }

    @SuppressWarnings("unchecked")
    private void resize(int newSize) {
        E[] copy = (E[]) new Object[newSize];
        System.arraycopy(array, 0, copy, 0, currentSize);
        array = copy;
    }

    @Override
    public String toString() {
        if (currentSize == 0) {
            return "[]";
        }
        Object[] copy = new Object[currentSize];
        System.arraycopy(array, 0, copy, 0, currentSize);
        return Arrays.toString(copy);
    }
}
