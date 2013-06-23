package problem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

abstract public class AbstractTrainingProblem5Test {

	private TrainingProblem5<Integer> problem;

	@Before
	public void setUp() {
		problem = create();
	}

	abstract protected TrainingProblem5<Integer> create();

	@Test
	public void testProblemSameLists() {
		List<Integer> l1 = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		List<Integer> l2 = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9));
		List<Integer> intersection = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
		assertThat(problem.intersection(l1, l2), is(intersection));
	}

	@Test
	public void testProblemEmptyIntersection() {
		List<Integer> l1 = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4));
		List<Integer> l2 = new LinkedList<>(Arrays.asList(5, 6, 7, 8, 9));
		List<Integer> intersection = Arrays.asList();
		assertThat(problem.intersection(l1, l2), is(intersection));
	}

	@Test
	public void testProblemIntersection() {
		List<Integer> l1 = new LinkedList<>(Arrays.asList(0, 1, 2, 3, 4));
		List<Integer> l2 = new LinkedList<>(Arrays.asList(5, 2, 7, 0, 9));
		List<Integer> intersection = Arrays.asList(0, 2);
		assertThat(problem.intersection(l1, l2), is(intersection));
	}

	@Test
	public void testProblemHugeIntersection() {
		List<Integer> l1 = new LinkedList<>();
		for (int i = 0; i < 100000; i += 2) {
			l1.add(i);
		}
		List<Integer> l2 = new LinkedList<>();
		for (int i = 0; i < 100000; i += 3) {
			l2.add(i);
		}
		List<Integer> intersection = new LinkedList<>();
		for (int i = 0; i < 100000; i += 6) {
			intersection.add(i);
		}
		assertThat(problem.intersection(l1, l2), is(intersection));
	}
}
