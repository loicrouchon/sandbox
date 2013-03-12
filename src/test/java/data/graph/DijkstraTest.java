package data.graph;

public class DijkstraTest extends AbstractDijkstraTest {

    @Override
    protected DijkstraAlgorithm createDijkstraAlgorithm() {
        return new Dijkstra();
    }
}
