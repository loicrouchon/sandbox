package data.graph;

import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    public static class Node {
        private final int key;
        private final int posX;
        private final int posY;
        private final Collection<Edge> edges = new LinkedList<Edge>();

        public Node(int key, int posX, int posY) {
            this.key = key;
            this.posX = posX;
            this.posY = posY;
        }

        public void connectTo(Node node) {
            int weight = (int) (1.1 * estimatedDistance(node));
            edges.add(new Edge(node, weight));
        }

        public int estimatedDistance(Node node) {
            return (int) Math.sqrt(Math.pow(posX - node.posX, 2) + Math.pow(posY - node.posY, 2));
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
            return "{" + to.key + ":" + weight + "}";
        }
    }

    static class EstimatedDistance {
        int nodeIndex;
        int distance;

        EstimatedDistance(int nodeIndex, int distance) {
            this.nodeIndex = nodeIndex;
            this.distance = distance;
        }

        @Override
        public int hashCode() {
            return nodeIndex;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof EstimatedDistance && ((EstimatedDistance) o).nodeIndex == nodeIndex;
        }

        @Override
        public String toString() {
            return "{" + nodeIndex + ":" + distance + "}";
        }

        static Comparator<EstimatedDistance> comparator = new Comparator<EstimatedDistance>() {
            public int compare(EstimatedDistance a, EstimatedDistance b) {
                return a.distance < b.distance ? -1 : (a.distance == b.distance ? 0 : 1);
            }
        };
    }

    public List<Node> shortestPath(Node[] nodes, int startIndex, int endIndex) {
        int[] predecessors = new int[nodes.length];
        Arrays.fill(predecessors, -1);
        int[] distances = new int[nodes.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        predecessors[startIndex] = startIndex;
        distances[startIndex] = 0;
        PriorityQueue<EstimatedDistance> nodesToVisit = new PriorityQueue<EstimatedDistance>(nodes.length,
                EstimatedDistance.comparator);
        if (startIndex != endIndex) {
            nodesToVisit.add(new EstimatedDistance(startIndex, nodes[startIndex].estimatedDistance(nodes[endIndex])));
        }
        while (!nodesToVisit.isEmpty()) {
            int nodeIndex = nodesToVisit.poll().nodeIndex;
            for (Edge edge : nodes[nodeIndex].getEdges()) {
                Node child = edge.getTo();
                int childIndex = getNodeIndex(child, nodes);
                int distance = distances[nodeIndex] + edge.getWeight();
                if (distance < distances[childIndex]) {
                    distances[childIndex] = distance;
                    predecessors[childIndex] = nodeIndex;
                    if (childIndex == endIndex) {
                        nodesToVisit.clear();
                        break;
                    }
                    int estimatedDistance = distance + child.estimatedDistance(nodes[endIndex]);
                    EstimatedDistance erd = new EstimatedDistance(childIndex, estimatedDistance);
                    if (!nodesToVisit.contains(erd)) {
                        nodesToVisit.add(erd);
                    }
                }
            }
        }
        LinkedList<Node> path = new LinkedList<Node>();
        int nodeIndex = endIndex;
        while (nodeIndex != startIndex) {
            path.addFirst(nodes[nodeIndex]);
            nodeIndex = predecessors[nodeIndex];
            if (nodeIndex == -1) {
                throw new IllegalStateException("Node is unreachable");
            }
        }
        path.addFirst(nodes[startIndex]);
        return path;
    }

    private int getNodeIndex(Node node, Node[] nodes) {
        for (int i = 0; i < nodes.length; i++) {
            if (nodes[i] == node) {
                return i;
            }
        }
        throw new IllegalStateException("Getting out the graph");
    }
}