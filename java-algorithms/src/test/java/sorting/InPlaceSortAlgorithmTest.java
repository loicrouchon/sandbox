package sorting;

import org.junit.Before;

public abstract class InPlaceSortAlgorithmTest extends AbstractSortAlgorithmTest {

    private InPlaceSortAlgorithm sortAlgorithm;

    @Before
    public void setUp() {
        sortAlgorithm = createSortingAlgorithm();
    }

    abstract InPlaceSortAlgorithm createSortingAlgorithm();

    @Override
    int[] sort(int[] actuals) {
        sortAlgorithm.sort(actuals);
        return actuals;
    }
}
