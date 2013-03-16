package sorting;

public class QuickSelectionSortRandomPivotTest extends InPlaceSortAlgorithmTest {

    @Override
    protected InPlaceSortAlgorithm createSortingAlgorithm() {
        return new QuickSelectionSortRandomPivot(10);
    }
}
