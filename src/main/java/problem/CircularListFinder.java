package problem;
/**
 * You are given a list of numbers. When you reach the end of the list you will
 * come back to the beginning of the list (a circular list). Write the most
 * efficient algorithm to find the minimum # in this list. Find any given # in
 * the list. The numbers in the list are always increasing but you don�t know
 * where the circular list begins, ie: 38, 40, 55, 89, 6, 13, 20, 23, 36.
 */
public class CircularListFinder {

	public int findMinimum(int[] array) {
		if (array.length == 0) {
			return -1;
		}
		int start = 0;
		int end = array.length - 1;
		while (end > start) {
			int middle = (start + end) / 2;
			if (array[middle] < array[end]) {
				end = middle;
			} else {
				start = middle + 1;
			}
		}
		return array[start];
	}

	public int indexOf(int[] array, int value) {
		if (array.length == 0) {
			return -1;
		}
		int start = 0;
		int end = array.length - 1;
		while (end > start) {
			int middle = (start + end) / 2;
			if ((array[middle] < array[end] && array[middle] < value && value <= array[end])
			        || (array[middle] > array[end] && (value > array[middle] || (value <= array[end] && value != array[middle])))) {
				start = middle + 1;
			} else {
				end = middle;
			}

		}
		return start;
	}
}