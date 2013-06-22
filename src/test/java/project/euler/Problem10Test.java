package project.euler;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

public class Problem10Test {

	@Test
	public void test() {
		assertThat(Problem10.sumOfPrimesBelow(10), is(17L));
		assertThat(Problem10.sumOfPrimesBelow(2000000), is(142913828922L));
	}
}
