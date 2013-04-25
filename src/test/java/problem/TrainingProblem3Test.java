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
    public void testMaxSubMatrix1() {
        int[][] matrix = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, };
        int[][] expecteds = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, 6 }, };
        int[][] actuals = trainingProblem3.maxSubMatrix(matrix);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testMaxSubMatrix2() {
        int[][] matrix = new int[][] { new int[] { 1, 2, 3 }, new int[] { 4, 5, -6 }, };
        int[][] expecteds = new int[][] { new int[] { 1, 2 }, new int[] { 4, 5 }, };
        int[][] actuals = trainingProblem3.maxSubMatrix(matrix);
        assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testMaxSubMatrix3() {
        int[][] matrix = new int[][] { new int[] { -4, 5, 3 }, new int[] { 2, 4, -6 }, };
        int[][] expecteds = new int[][] { new int[] { 5 }, new int[] { 4 }, };
        int[][] actuals = trainingProblem3.maxSubMatrix(matrix);
        assertArrayEquals(expecteds, actuals);
    }
}
