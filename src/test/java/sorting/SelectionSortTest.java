package sorting;

public class SelectionSortTest extends InPlaceSortAlgorithmTest {

    @Override
    protected InPlaceSortAlgorithm createSortingAlgorithm() {
        return new SelectionSort();
    }

}
