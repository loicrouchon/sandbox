package sorting;

public class QuickSortRandomPivotTest extends InPlaceSortAlgorithmTest {

    @Override
    protected InPlaceSortAlgorithm createSortingAlgorithm() {
        return new QuickSortRandomPivot();
    }
}
