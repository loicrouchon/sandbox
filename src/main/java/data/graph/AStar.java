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
    }

    static class EstimatedRemainingDistance {
        int nodeIndex;
        int distance;

        EstimatedRemainingDistance(int nodeIndex, int distance) {
            this.nodeIndex = nodeIndex;
            this.distance = distance;
        }

        @Override
        public int hashCode() {
            return nodeIndex;
        }

        @Override
        public boolean equals(Object o) {
            return o instanceof EstimatedRemainingDistance && ((EstimatedRemainingDistance) o).nodeIndex == nodeIndex;
        }

        static Comparator<EstimatedRemainingDistance> comparator = new Comparator<EstimatedRemainingDistance>() {
            public int compare(EstimatedRemainingDistance a, EstimatedRemainingDistance b) {
                return a.distance < b.distance ? -1 : (a.distance == b.distance ? 0 : 1);
            }
        };
    }

    public List<Node> shortestPath(Node[] nodes, int startIndex, int endIndex) {
        int[] predecessors = new int[nodes.length];
        Arrays.fill(predecessors, -1);
        int[] distances = new int[nodes.length];
        Arrays.fill(distances, Integer.MAX_VALUE);
        int[] estimatedRemainingDistances = new int[nodes.length];
        Arrays.fill(estimatedRemainingDistances, Integer.MAX_VALUE);
        predecessors[startIndex] = startIndex;
        distances[startIndex] = 0;
        estimatedRemainingDistances[startIndex] = nodes[startIndex].estimatedDistance(nodes[endIndex]);
        PriorityQueue<EstimatedRemainingDistance> nodesToVisit = new PriorityQueue<EstimatedRemainingDistance>(
                nodes.length, EstimatedRemainingDistance.comparator);
        nodesToVisit.add(new EstimatedRemainingDistance(startIndex, estimatedRemainingDistances[startIndex]));
        while (!nodesToVisit.isEmpty()) {
            int nodeIndex = nodesToVisit.poll().nodeIndex;
            if (nodeIndex == endIndex) {
                break;
            }
            for (Edge edge : nodes[nodeIndex].getEdges()) {
                Node child = edge.getTo();
                int childIndex = getNodeIndex(child, nodes);
                int distance = distances[nodeIndex] + edge.getWeight();
                if (distance < distances[childIndex]) {
                    distances[childIndex] = distances[nodeIndex] + edge.getWeight();
                    predecessors[childIndex] = nodeIndex;
                    estimatedRemainingDistances[childIndex] = child.estimatedDistance(nodes[endIndex]);
                    EstimatedRemainingDistance erd = new EstimatedRemainingDistance(childIndex,
                            estimatedRemainingDistances[childIndex]);
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