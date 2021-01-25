package project.euler;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class Problem7Test {

	@Test
	public void testProblem() {
		assertEquals(13, Problem7.exec(6));
		assertEquals(104743, Problem7.exec(10001));
		assertEquals(15485863, Problem7.exec(1000000));
	}
}
