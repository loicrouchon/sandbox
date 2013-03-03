package sorting;

public class BubbleSortTest extends InPlaceSortTest {

    @Override
    protected InPlaceSort createSortingAlgorithm() {
        return new BubbleSort();
    }

}
