package data.priorityqueue;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Comparator;

import org.junit.Test;

public class HeapTest {

	private final class IntegerComparator implements Comparator<Integer> {
		@Override
		public int compare(Integer a, Integer b) {
			return a.compareTo(b);
		}
	}

	@Test
	public void testEmptyHeap() {
		Heap<Integer> heap = new Heap<>(new IntegerComparator());
		assertThat(heap.isEmpty(), is(true));
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyHeapPeek() {
		Heap<Integer> heap = new Heap<>(new IntegerComparator());
		heap.peek();
	}

	@Test(expected = IllegalStateException.class)
	public void testEmptyHeapPop() {
		Heap<Integer> heap = new Heap<>(new IntegerComparator());
		heap.pop();
	}

	@Test
	public void testHeapWithOneElement() {
		Heap<Integer> heap = new Heap<>(new IntegerComparator());
		heap.add(7);
		assertThat(heap.isEmpty(), is(false));
		assertThat(heap.peek(), is(7));
		assertThat(heap.isEmpty(), is(false));
		assertThat(heap.pop(), is(7));
		assertThat(heap.isEmpty(), is(true));
	}

	@Test
	public void testHeapWithSeveralElements() {
		Heap<Integer> heap = new Heap<>(new IntegerComparator());
		heap.add(new Integer[] { 7, 4, 5, 8, 1, 9, -5 });
		for (Integer i : new Integer[] { -5, 1, 4, 5, 7, 8, 9 }) {
			assertThat(heap.isEmpty(), is(false));
			assertThat(heap.peek(), is(i));
			assertThat(heap.isEmpty(), is(false));
			assertThat(heap.pop(), is(i));
		}
		assertThat(heap.isEmpty(), is(true));
	}
}
