package problem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import problem.TrainingProblem8.Node;

public class TrainingProblem9Test {

	@Test
	public void testEmptryTree() {
		TrainingProblem9 problem = new TrainingProblem9();
		assertThat(problem.columnNumber(""), is(0));
		assertThat(problem.columnNumber("A"), is(1));
		assertThat(problem.columnNumber("B"), is(2));
		assertThat(problem.columnNumber("C"), is(3));
		assertThat(problem.columnNumber("D"), is(4));
		assertThat(problem.columnNumber("E"), is(5));
		assertThat(problem.columnNumber("F"), is(6));
		assertThat(problem.columnNumber("G"), is(7));
		assertThat(problem.columnNumber("H"), is(8));
		assertThat(problem.columnNumber("I"), is(9));
		assertThat(problem.columnNumber("J"), is(10));
		assertThat(problem.columnNumber("K"), is(11));
		assertThat(problem.columnNumber("L"), is(12));
		assertThat(problem.columnNumber("M"), is(13));
		assertThat(problem.columnNumber("N"), is(14));
		assertThat(problem.columnNumber("O"), is(15));
		assertThat(problem.columnNumber("P"), is(16));
		assertThat(problem.columnNumber("Q"), is(17));
		assertThat(problem.columnNumber("R"), is(18));
		assertThat(problem.columnNumber("S"), is(19));
		assertThat(problem.columnNumber("T"), is(20));
		assertThat(problem.columnNumber("U"), is(21));
		assertThat(problem.columnNumber("V"), is(22));
		assertThat(problem.columnNumber("W"), is(23));
		assertThat(problem.columnNumber("X"), is(24));
		assertThat(problem.columnNumber("Y"), is(25));
		assertThat(problem.columnNumber("Z"), is(26));
		assertThat(problem.columnNumber("AA"), is(27));
		assertThat(problem.columnNumber("AB"), is(28));
		assertThat(problem.columnNumber("AAA"), is(703));
		assertThat(problem.columnNumber("ZZZ"), is(18278));
	}
}
