package project.euler;


public class Problem7 {

	public static long exec(int iestPrime) {
		long iestPrimeValue = 0;
		int sqrtIestPrime = (int) Math.sqrt(iestPrime);
		long[] primes = new long[sqrtIestPrime];
		int nbElts = 0;
		primes[nbElts++] = 2;
		primes[nbElts++] = 3;
		int[] ls = new int[] { -1, 1 };
		for (long k = 1; nbElts < iestPrime; k++) {
			for (int l : ls) {
				long n = 6 * k + l;
				if (isPrime(n, primes, nbElts < sqrtIestPrime ? nbElts : sqrtIestPrime)) {
					if (nbElts < sqrtIestPrime) {
						primes[nbElts] = n;
					} else if (nbElts == iestPrime - 1) {
						iestPrimeValue = n;
					}
					nbElts++;
				}
			}
		}
		return iestPrimeValue;
	}

	private static boolean isPrime(long n, long[] primes, int nbElts) {
		long sqrtN = (long) Math.sqrt(n);
		for (int i = 1; i < nbElts; i++) {
			if (n % primes[i] == 0) {
				return false;
			} else if (primes[i] > sqrtN) {
				break;
			}
		}
		return true;
	}
}