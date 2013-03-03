package sorting;


public class QuickSortTest extends InPlaceSortTest {

    @Override
    protected InPlaceSort createSortingAlgorithm() {
        return new QuickSort();
    }
}
