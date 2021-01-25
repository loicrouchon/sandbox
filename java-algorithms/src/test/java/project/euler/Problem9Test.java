package project.euler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import project.euler.Problem9.Triplet;

public class Problem9Test {

	@Test
	public void test() {
		assertThat(Problem9.firstPythagoreanTriplet(12), is(new Triplet(3, 4, 5)));
		assertThat(Problem9.firstPythagoreanTriplet(1000), is(new Triplet(200, 375, 425)));
	}
}
