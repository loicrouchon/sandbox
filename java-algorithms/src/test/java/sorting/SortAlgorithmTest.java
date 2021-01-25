package sorting;

import org.junit.Before;

public abstract class SortAlgorithmTest extends AbstractSortAlgorithmTest {

    private SortAlgorithm sortAlgorithm;

    @Before
    public void setUp() {
        sortAlgorithm = createSortingAlgorithm();
    }

    abstract SortAlgorithm createSortingAlgorithm();

    @Override
    int[] sort(int[] actuals) {
        return sortAlgorithm.sort(actuals);
    }
}
