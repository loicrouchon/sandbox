package sorting;


public class QuickSortTest extends InPlaceSortAlgorithmTest {

    @Override
    protected InPlaceSortAlgorithm createSortingAlgorithm() {
        return new QuickSort();
    }
}
