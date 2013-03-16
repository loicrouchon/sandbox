package sorting;

import java.util.Arrays;

import org.junit.Assert;
import org.junit.Test;

public class QuickSortBenchTest {

    private final InPlaceSortAlgorithm[] algorithms = new InPlaceSortAlgorithm[] { new QuickSort(),
            new QuickSortRandomPivot(), new QuickSelectionSortRandomPivot(5), new QuickSelectionSortRandomPivot(6),
            new QuickSelectionSortRandomPivot(7), new QuickSelectionSortRandomPivot(8),
            new QuickSelectionSortRandomPivot(9), new QuickSelectionSortRandomPivot(10),
            new QuickSelectionSortRandomPivot(11), new QuickSelectionSortRandomPivot(12),
            new QuickSelectionSortRandomPivot(13), new QuickSelectionSortRandomPivot(14),
            new QuickSelectionSortRandomPivot(15), new QuickSelectionSortRandomPivot(20),
            new QuickSelectionSortRandomPivot(40) };

    @Test
    public void testSortOnManyElements() {
        int size = 100000000;
        int[] expecteds = new int[size];
        for (int i = 0; i < size; i++) {
            int value = (int) (Math.random() * Integer.MAX_VALUE * (Math.random() < 0.5 ? -1d : 1d));
            expecteds[i] = value;
        }
        for (InPlaceSortAlgorithm algorithm : algorithms) {
            int[] actuals = Arrays.copyOf(expecteds, expecteds.length);
            Arrays.sort(expecteds);
            int[] sorted = sort(algorithm, actuals);
            Assert.assertArrayEquals(expecteds, sorted);
        }
    }

    private int[] sort(InPlaceSortAlgorithm algorithm, int[] array) {
        long start = System.currentTimeMillis();
        algorithm.sort(array);
        long end = System.currentTimeMillis();
        System.out.println(algorithm + ": " + (end - start) + "ms");
        return array;
    }

}
