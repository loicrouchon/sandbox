package problem;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class FizzBuzzTest {

	private FizzBuzz fizzBuzz;

	@Before
	public void setUp() {
		fizzBuzz = new FizzBuzz();
	}

	@Test
	public void testMin() {
		assertArrayEquals(new String[] {}, fizzBuzz.fizzBuzz(0));
		assertArrayEquals(new String[] { "1" }, fizzBuzz.fizzBuzz(1));
		assertArrayEquals(new String[] { "1", "2" }, fizzBuzz.fizzBuzz(2));
		assertArrayEquals(new String[] { "1", "2", "Fizz" }, fizzBuzz.fizzBuzz(3));
		assertArrayEquals(new String[] { "1", "2", "Fizz", "4" }, fizzBuzz.fizzBuzz(4));
		assertArrayEquals(new String[] { "1", "2", "Fizz", "4", "Buzz" }, fizzBuzz.fizzBuzz(5));
		assertArrayEquals(new String[] { "1", "2", "Fizz", "4", "Buzz", "Fizz", "7", "8", "Fizz", "Buzz", "11", "Fizz",
		        "13", "14", "Fizz Buzz", "16" }, fizzBuzz.fizzBuzz(16));
	}
}
