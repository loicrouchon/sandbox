package sorting;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

public abstract class InPlaceSortAlgorithmTest {

    private InPlaceSortAlgorithm sortAlgorithm;

    @Before
    public void setUp() {
        sortAlgorithm = createSortingAlgorithm();
    }

    protected abstract InPlaceSortAlgorithm createSortingAlgorithm();

    @Test
    public void testEmptySort() {
        int[] expecteds = new int[0];
        int[] actuals = new int[0];
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testSortOnSingleElement() {
        int[] expecteds = new int[] { 1 };
        int[] actuals = new int[] { 1 };
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testSortOnTwosortedElements() {
        int[] expecteds = new int[] { 1, 2 };
        int[] actuals = new int[] { 1, 2 };
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testSortOnTwoUnsortedElements() {
        int[] expecteds = new int[] { 1, 2 };
        int[] actuals = new int[] { 2, 1 };
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testSortOnManyElements1() {
        int[] expecteds = new int[] { 1, 2, 3, 3, 4, 5, 8, 11 };
        int[] actuals = new int[] { 3, 2, 11, 5, 1, 8, 3, 4 };
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testSortOnManyElements2() {
        int[] expecteds = new int[] { 1, 2, 3, 4, 5, 5, 7, 8, 9 };
        int[] actuals = new int[] { 3, 7, 8, 5, 2, 1, 9, 5, 4 };
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testSortOnManyElements3() {
        int size = 100000;
        int[] expecteds = new int[size];
        for (int i = 0; i < size; i++) {
            int value = (int) (Math.random() * Integer.MAX_VALUE * (Math.random() < 0.5 ? -1d : 1d));
            expecteds[i] = value;
        }
        int[] actuals = Arrays.copyOf(expecteds, expecteds.length);
        Arrays.sort(expecteds);
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }

    @Test
    public void testSortOnManyElements4() {
        int[] expecteds = new int[] { 2, 4, 5, 6, 7, 8, 9, 11, 12, 13, 19, 21 };
        int[] actuals = new int[] { 13, 19, 9, 5, 12, 8, 7, 4, 11, 2, 6, 21 };
        sortAlgorithm.sort(actuals);
        Assert.assertArrayEquals(expecteds, actuals);
    }
}
