package problem;

public class TrainingProblem3 {

	/*
	 * There is an array A[N] of N numbers.
	 * 
	 * You have to compose an array Output[N] such that Output[i] will be equal
	 * to multiplication of all the elements of A[N] except A[i].
	 * 
	 * For example Output[0] will be multiplication of A[1] to A[N-1] and
	 * Output[1] will be multiplication of A[0] and from A[2] to A[N-1].
	 * 
	 * Solve it without division operator and in O(n).
	 */
	public int[] multiply(int[] array) {
		int n = array.length;
		int[] results = new int[n];
		if (n > 0) {
			int[] previousProducts = new int[n];
			int[] nextProducts = new int[n];
			previousProducts[0] = 1;
			nextProducts[n - 1] = 1;
			for (int i = 1; i < n; i++) {
				previousProducts[i] = previousProducts[i - 1] * array[i - 1];
				nextProducts[n - (i + 1)] = nextProducts[n - i] * array[n - i];
			}
			for (int i = 0; i < n; i++) {
				results[i] = previousProducts[i] * nextProducts[i];
			}
		}
		return results;
	}
}
