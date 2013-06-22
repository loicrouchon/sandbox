package data.priorityqueue;

import java.util.Comparator;

public class Heap<E> {

	private final Comparator<E> comparator;

	private int size = 0;

	private E[] data = createArray(10);

	private static <E> E[] createArray(int newSize) {
		return (E[]) new Object[newSize];
	}

	public Heap(Comparator<E> comparator) {
		this.comparator = comparator;
	}

	public boolean isEmpty() {
		return size == 0;
	}

	public E peek() {
		if (isEmpty()) {
			throw new IllegalStateException();
		}
		return data[0];
	}

	public E pop() {
		E item = peek();
		int lastIndex = --size;
		data[0] = data[lastIndex];
		data[lastIndex] = null;
		bubbleDown(0);
		return item;
	}

	private void bubbleDown(int index) {
		int leftChildIndex = 2 * (index + 1) - 1;
		int minIndex = index;
		for (int i = leftChildIndex; i <= leftChildIndex + 1; i++) {
			if (i >= size) {
				break;
			} else if (comparator.compare(data[minIndex], data[i]) > 0) {
				minIndex = i;
			}
		}
		if (minIndex != index) {
			swap(index, minIndex);
			bubbleDown(minIndex);
		}
	}

	public void add(E[] items) {
		for (E item : items) {
			add(item);
		}
	}

	public void add(E item) {
		int index = size;
		increaseSize();
		data[index] = item;
		bubbleUp(index);
	}

	private void increaseSize() {
		size++;
		while (size > data.length) {
			E[] copy = createArray(data.length * 2);
			System.arraycopy(data, 0, copy, 0, data.length);
			data = copy;
		}
	}

	private void bubbleUp(int index) {
		if (index > 0) {
			int parentIndex = index / 2;
			if (comparator.compare(data[index], data[parentIndex]) < 0) {
				swap(index, parentIndex);
				bubbleUp(parentIndex);
			}
		}
	}

	private void swap(int a, int b) {
		E tmp = data[a];
		data[a] = data[b];
		data[b] = tmp;
	}
}
