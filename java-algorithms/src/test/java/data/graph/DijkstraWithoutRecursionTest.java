package data.graph;

public class DijkstraWithoutRecursionTest extends DijkstraTest {

    @Override
    protected DijkstraAlgorithm createDijkstraAlgorithm() {
        return new DijkstraWithoutRecursion();
    }
}
