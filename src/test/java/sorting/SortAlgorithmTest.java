package sorting;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class SortAlgorithmTest {

    private SortAlgorithm sortAlgorithm;

    @Before
    public void setUp() {
        sortAlgorithm = createSortingAlgorithm();
    }

    protected abstract SortAlgorithm createSortingAlgorithm();

    @Test
    public void testEmptyQuickSort() {
        int[] expecteds = new int[0];
        int[] actuals = new int[0];
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testQuickSortOnSingleElement() {
        int[] expecteds = new int[] { 1 };
        int[] actuals = new int[] { 1 };
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testQuickSortOnTwosortedElements() {
        int[] expecteds = new int[] { 1, 2 };
        int[] actuals = new int[] { 1, 2 };
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testQuickSortOnTwoUnsortedElements() {
        int[] expecteds = new int[] { 1, 2 };
        int[] actuals = new int[] { 2, 1 };
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testQuickSortOnManyElements1() {
        int[] expecteds = new int[] { 1, 2, 3, 3, 4, 5, 8, 11 };
        int[] actuals = new int[] { 3, 2, 11, 5, 1, 8, 3, 4 };
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testQuickSortOnManyElements2() {
        int[] expecteds = new int[] { 1, 2, 3, 4, 5, 5, 7, 8, 9 };
        int[] actuals = new int[] { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testQuickSortOnManyElements3() {
        int size = 10000;
        int[] expecteds = new int[size];
        for (int i = 0; i < size; i++) {
            int value = (int) (Math.random() * Integer.MAX_VALUE * (Math.random() < 0.5 ? -1d : 1d));
            expecteds[i] = value;
        }
        int[] actuals = Arrays.copyOf(expecteds, expecteds.length);
        Arrays.sort(expecteds);
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testQuickSortOnManyElements4() {
        int[] expecteds = new int[] { 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 19, 21 };
        int[] actuals = new int[] { 13, 19, 9, 5, 12, 8, 7, 4, 11, 2, 6, 21 };
        actuals = sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }
}
