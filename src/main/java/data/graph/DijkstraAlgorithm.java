package data.graph;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public interface DijkstraAlgorithm {
    public static class Node {
        private final int key;
        private final Collection<Edge> edges = new LinkedList<Edge>();

        public Node(int key) {
            this.key = key;
        }

        public void connectTo(Node node, int weight) {
            edges.add(new Edge(node, weight));
        }

        public Collection<Edge> getEdges() {
            return edges;
        }

        @Override
        public String toString() {
            return Integer.toString(key);
        }
    }

    public static class Edge {
        private final Node to;
        private final int weight;

        public Edge(Node to, int weight) {
            this.to = to;
            this.weight = weight;
        }

        public Node getTo() {
            return to;
        }

        public int getWeight() {
            return weight;
        }

        @Override
        public String toString() {
            return to.toString() + "(" + weight + ")";
        }
    }

    List<Node> shortestPath(Set<Node> nodes, Node a, Node b);
}