package project.euler;

public class Problem6 {

	public static long sumOfSquaresAndSquareSumDiff(int from, int to) {
		long sumOfSquares = computeSumOfSquares(from, to);
		long squareSum = computeSquareSum(from, to);
		return squareSum - sumOfSquares;
	}

	private static long computeSumOfSquares(int from, int to) {
		long result = 0;
		for (int i = from; i <= to; i++) {
			result += Math.pow(i, 2);
		}
		return result;
	}

	private static long computeSquareSum(int from, int to) {
		int sum = 0;
		for (int i = from; i <= to; i++) {
			sum += i;
		}
		return Double.valueOf(Math.pow(sum, 2)).longValue();
	}
}