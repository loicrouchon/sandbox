package problem;

import static org.junit.Assert.assertArrayEquals;

import org.junit.Before;
import org.junit.Test;

public class TrainingProblem3Test {

    private TrainingProblem3 trainingProblem3;

    @Before
    public void setUp() {
        trainingProblem3 = new TrainingProblem3();
    }

    @Test
    public void testMin() {
        test(new int[] {}, new int[] {});
        test(new int[] { 1 }, new int[] { 5 });
        test(new int[] { 120, 60, 40, 30, 24 }, new int[] { 1, 2, 3, 4, 5 });
        test(new int[] { 56, 112, 224, 56, 32 }, new int[] { 4, 2, 1, 4, 7 });
    }

    private void test(int[] expected, int[] array) {
        assertArrayEquals(expected, trainingProblem3.multiply(array));
    }
}
