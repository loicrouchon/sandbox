package problem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class CircularListFinderTest {

	@Test
	public void testFindMinimum() {
		CircularListFinder circularListFinder = new CircularListFinder();
		assertThat(circularListFinder.findMinimum(new int[] {}), is(-1));
		assertThat(circularListFinder.findMinimum(new int[] { 1 }), is(1));
		assertThat(circularListFinder.findMinimum(new int[] { 1, 2 }), is(1));
		assertThat(circularListFinder.findMinimum(new int[] { 3, 1, 2 }), is(1));
		assertThat(circularListFinder.findMinimum(new int[] { 2, 3, 1 }), is(1));
		assertThat(circularListFinder.findMinimum(new int[] { 2, 3, 0, 1 }), is(0));
		assertThat(circularListFinder.findMinimum(new int[] { 1, 2, 3, 0 }), is(0));
	}

	@Test
	public void testIndexOf() {
		CircularListFinder circularListFinder = new CircularListFinder();
		int[] array = new int[] { 1, 2, 3, 0 };
		assertThat(circularListFinder.indexOf(array, 1), is(0));
		assertThat(circularListFinder.indexOf(array, 2), is(1));
		assertThat(circularListFinder.indexOf(array, 3), is(2));
		assertThat(circularListFinder.indexOf(array, 4), is(3));
		array = new int[] { 38, 40, 55, 89, 6, 13, 20, 23, 36 };
		assertThat(circularListFinder.indexOf(array, 38), is(0));
		assertThat(circularListFinder.indexOf(array, 40), is(1));
		assertThat(circularListFinder.indexOf(array, 55), is(2));
		assertThat(circularListFinder.indexOf(array, 89), is(3));
		assertThat(circularListFinder.indexOf(array, 6), is(4));
		assertThat(circularListFinder.indexOf(array, 13), is(5));
		assertThat(circularListFinder.indexOf(array, 20), is(6));
		assertThat(circularListFinder.indexOf(array, 23), is(7));
		assertThat(circularListFinder.indexOf(array, 36), is(8));
	}

}
