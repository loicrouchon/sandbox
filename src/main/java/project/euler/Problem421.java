package project.euler;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Problem421 {

	public BigInteger sumS(BigInteger start, BigInteger end, BigInteger m, int nbThreads) throws InterruptedException {
		final List<BigInteger> primes = Problem421.findAllPrimesBelow(m.intValue());
		return new Problem421().sumS(start, end, m, primes, nbThreads);
	}

	public BigInteger sumS(BigInteger start, BigInteger end, BigInteger m, List<BigInteger> primes, int nbThreads)
	        throws InterruptedException {
		long startTime = System.currentTimeMillis();
		final BlockingQueue<BigInteger> results = new ArrayBlockingQueue<BigInteger>(nbThreads);
		for (int i = 0; i < nbThreads; i++) {
			new Thread(new RunnableSum(start, end, m, BigInteger.valueOf(i), BigInteger.valueOf(nbThreads), primes,
			        results)).start();
		}
		BigInteger sum = BigInteger.ZERO;
		for (int i = 0; i < nbThreads; i++) {
			sum = sum.add(results.take());
		}
		long endTime = System.currentTimeMillis();
		System.out
		        .println("sum: " + sum + ", duration: " + (endTime - startTime) / 1000 + "s, nbThreads: " + nbThreads);
		return sum;
	}

	private final class RunnableSum implements Runnable {

		private final BigInteger start;
		private final BigInteger end;
		private final BigInteger m;
		private final BigInteger shift;
		private final BigInteger increment;
		private final List<BigInteger> primes;
		private final BlockingQueue<BigInteger> results;

		private RunnableSum(BigInteger start, BigInteger end, BigInteger m, BigInteger shift, BigInteger increment,
		        List<BigInteger> primes, BlockingQueue<BigInteger> results) {
			this.start = start;
			this.end = end;
			this.m = m;
			this.shift = shift;
			this.increment = increment;
			this.primes = primes;
			this.results = results;
		}

		public void run() {
			BigInteger thousand = BigInteger.valueOf(1000);
			BigInteger sum = BigInteger.ZERO;
			for (BigInteger n = start.add(shift); n.compareTo(end) <= 0; n = n.add(increment)) {
				// System.out.println("processing: shift=" + shift + ", n=" +
				// n);
				BigInteger s = n.pow(15).add(BigInteger.ONE);
				sum = sum.add(sumPrimeDivisors(s, m, primes));
				if (n.mod(thousand).equals(BigInteger.ZERO)) {
					System.out.println("processing: shift=" + shift + ", n=" + n + ", sum=" + sum);
				}
			}
			try {
				results.put(sum);
			} catch (InterruptedException e) {
				throw new RuntimeException("ERROR: boom, not able to put sum=" + sum);
			}
		}
	}

	public BigInteger s(BigInteger n, BigInteger m) {
		BigInteger s = n.pow(15).add(BigInteger.ONE);
		List<BigInteger> primes = findAllPrimesBelow(m.intValue());
		BigInteger sumPrimeDivisors = sumPrimeDivisors(s, m, primes);
		return sumPrimeDivisors;
	}

	public BigInteger sumPrimeDivisors(BigInteger s, BigInteger m, List<BigInteger> primes) {
		BigInteger sum = BigInteger.ZERO;
		BigInteger divided = s;
		BigInteger maxPrime = BigInteger.valueOf((int) Math.sqrt(divided.doubleValue()));
		for (BigInteger prime : primes) {
			if (prime.compareTo(maxPrime) > 0) {
				sum = sum.add(divided);
				break;
			}
			boolean divisible = divided.mod(prime).equals(BigInteger.ZERO);
			if (divisible) {
				sum = sum.add(prime);
				while (divisible) {
					divided = divided.divide(prime);
					divisible = divided.mod(prime).equals(BigInteger.ZERO);
				}
				if (divided.equals(BigInteger.ONE)) {
					break;
				}
				maxPrime = BigInteger.valueOf((int) Math.sqrt(divided.doubleValue()));
			}
		}
		return sum;
	}

	public static List<BigInteger> findAllPrimesBelow(int m) {
		List<Integer> primes = new ArrayList<Integer>(m);
		primes.add(2);
		primes.add(3);
		int[] ls = new int[] { -1, 1 };
		int max = m / 6;
		for (int k = 1; k <= max; k++) {
			for (int l : ls) {
				int n = 6 * k + l;
				if (isPrime(n, primes)) {
					primes.add(n);
				}
			}
		}
		List<BigInteger> bigPrimes = new ArrayList<BigInteger>(primes.size());
		for (Integer prime : primes) {
			bigPrimes.add(BigInteger.valueOf(prime));
		}
		return bigPrimes;
	}

	private static boolean isPrime(int n, List<Integer> primes) {
		int sqrtN = (int) Math.sqrt(n);
		for (Integer k : primes) {
			if (n % k == 0) {
				return false;
			} else if (k > sqrtN) {
				break;
			}
		}
		return true;
	}
}