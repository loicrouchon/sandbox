package problem;

import static org.junit.Assert.assertArrayEquals;

import java.util.Arrays;

import org.junit.Before;
import org.junit.Test;

public class TrainingProblem4Test {

    private TrainingProblem4 trainingProblem;

    @Before
    public void setUp() {
        trainingProblem = new TrainingProblem4();
    }

    @Test
    public void testMaxSubMatrix1() {
        Object[] array = new Object[] { 1, 2, 3, 4, 'a', 'b', 'c', 'd' };
        Object[] expecteds = new Object[] { 1, 'a', 2, 'b', 3, 'c', 4, 'd' };
        Object[] actuals = Arrays.copyOf(array, array.length);
        trainingProblem.sortIntegersAndChars(actuals);
        assertArrayEquals(expecteds, actuals);
    }
}
