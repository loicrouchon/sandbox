package problem;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

public class TrainingProblem2Test {

    private TrainingProblem2 trainingProblem2;

    @Before
    public void setUp() {
        trainingProblem2 = new TrainingProblem2();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testEmptyArray() {
        trainingProblem2.minN(new int[0]);
    }

    @Test
    public void testMin() {
        test(5, new int[] { 5 });
        test(1, new int[] { 1, 2, 3, 4 });
        test(1, new int[] { 2, 3, 4, 1 });
        test(1, new int[] { 3, 4, 1, 2, });
        test(1, new int[] { 4, 1, 2, 3 });
        test(1, new int[] { 1, 2, 3, 4, 5 });
        test(1, new int[] { 2, 3, 4, 5, 1 });
        test(1, new int[] { 3, 4, 5, 1, 2, });
        test(1, new int[] { 4, 5, 1, 2, 3 });
        test(1, new int[] { 5, 1, 2, 3, 4 });
        test(1, new int[] { 1, 2, 3, 4, 5, 6, 7 });
        test(1, new int[] { 2, 3, 4, 5, 6, 7, 1 });
        test(6, new int[] { 38, 40, 55, 89, 6, 13, 20, 23, 36 });
        test(17, bigArray(0, 17));
        test(478, bigArray(53, 478));
        test(987, bigArray(20000000, 987));
        test(987, bigArray(50000000, 987));
        test(42, bigArray(99999999, 42));
    }

    private int[] bigArray(int shift, int min) {
        int[] array = new int[100000000];
        for (int i = 0; i < array.length; i++) {
            array[i] = (i + shift) % array.length + min;
        }
        return array;
    }

    private void test(int expected, int[] array) {
        long t1 = System.currentTimeMillis();
        assertEquals(expected, trainingProblem2.minN(array));
        long t2 = System.currentTimeMillis();
        System.out.println("duration O(n) n=" + array.length + " is " + (t2 - t1) + "ms");
        t1 = System.currentTimeMillis();
        assertEquals(expected, trainingProblem2.minLogN(array));
        t2 = System.currentTimeMillis();
        System.out.println("duration O(log(n)) n=" + array.length + " is " + (t2 - t1) + "ms");
    }
}
