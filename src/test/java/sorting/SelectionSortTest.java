package sorting;

public class SelectionSortTest extends InPlaceSortTest {

    @Override
    protected InPlaceSort createSortingAlgorithm() {
        return new SelectionSort();
    }

}
