package project.euler;

import static org.junit.Assert.assertEquals;

import java.math.BigInteger;
import java.util.List;

import org.junit.Test;

public class Problem421Test {

	@Test
	public void testProblem() throws InterruptedException {
		assertEquals(BigInteger.valueOf(3), new Problem421().s(BigInteger.valueOf(2), BigInteger.valueOf(10)));
		assertEquals(BigInteger.valueOf(345), new Problem421().s(BigInteger.valueOf(2), BigInteger.valueOf(1000)));
		assertEquals(BigInteger.valueOf(31), new Problem421().s(BigInteger.valueOf(10), BigInteger.valueOf(100)));
		assertEquals(BigInteger.valueOf(483), new Problem421().s(BigInteger.valueOf(10), BigInteger.valueOf(1000)));
		assertEquals(
		        new BigInteger("71352822618"),
		        new Problem421().sumS(BigInteger.valueOf(1), BigInteger.valueOf(10).pow(6),
		                BigInteger.valueOf(10).pow(8), 6));

		testThreads();
	}

	private void testThreads() throws InterruptedException {
		BigInteger m = BigInteger.valueOf(10).pow(8);
		long startTime = System.currentTimeMillis();
		System.out.println("finding primes");
		final List<BigInteger> primes = Problem421.findAllPrimesBelow(m.intValue());
		long endTime = System.currentTimeMillis();
		System.out.println("primes found in " + (endTime - startTime) / 1000.0 + "s");

		startTime = System.currentTimeMillis();
		System.out.println("s(10^11,10^8)");
		BigInteger sumPrimeDivisors = new Problem421().sumPrimeDivisors(
		        BigInteger.valueOf(10).pow(11).pow(15).add(BigInteger.ONE), BigInteger.valueOf(10).pow(8), primes);
		endTime = System.currentTimeMillis();
		System.out.println("s(10^11,10^8) = " + sumPrimeDivisors + " in " + (endTime - startTime) / 1000.0 + "s");

		for (int nbThreads = 6; nbThreads <= 6; nbThreads++) {
			new Problem421().sumS(BigInteger.valueOf(1), BigInteger.valueOf(10).pow(4), m, primes, nbThreads);
		}
	}
}
