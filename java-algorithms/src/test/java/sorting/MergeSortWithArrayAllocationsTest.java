package sorting;

public class MergeSortWithArrayAllocationsTest extends InPlaceSortAlgorithmTest {

    @Override
    protected InPlaceSortAlgorithm createSortingAlgorithm() {
        return new MergeSortWithArrayAllocations();
    }
}
