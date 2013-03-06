package sorting;

public class MergeSortTest extends InPlaceSortAlgorithmTest {

    @Override
    protected InPlaceSortAlgorithm createSortingAlgorithm() {
        return new MergeSort();
    }
}
