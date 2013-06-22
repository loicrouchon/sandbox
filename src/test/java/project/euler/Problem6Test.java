package project.euler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Problem6Test {

	@Test
	public void testSumOfSquaresAndSquareSumDiff() {
		assertEquals(2640, Problem6.sumOfSquaresAndSquareSumDiff(1, 10));
		assertEquals(25164150, Problem6.sumOfSquaresAndSquareSumDiff(1, 100));
	}
}
