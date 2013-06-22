package project.euler;

public class Problem10 {

	public static long sumOfPrimesBelow(int n) {
		long sum = 10;
		for (int i = 7; i <= n; i += 2) {
			if (isPrime(i)) {
				sum += i;
			}
		}
		return sum;
	}

	private static boolean isPrime(int n) {
		if (n % 3 == 0 || n % 5 == 0) {
			return false;
		}
		int sqrt = (int) Math.sqrt(n);
		for (int i = 7; i <= sqrt; i += 2) {
			if (n % i == 0) {
				return false;
			}
		}
		return true;
	}
}