package sorting;

public class BubbleSortTest extends InPlaceSortAlgorithmTest {

    @Override
    protected InPlaceSortAlgorithm createSortingAlgorithm() {
        return new BubbleSort();
    }

}
