package data.graph;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class Dijkstra implements DijkstraAlgorithm {

    public List<Node> shortestPath(Set<Node> nodes, Node a, Node b) {
        int nbNodes = nodes.size();
        Node[] indexedNodes = nodes.toArray(new Node[nbNodes]);
        int[] predecessors = new int[nbNodes];
        Arrays.fill(predecessors, -1);
        int[] distances = new int[nbNodes];
        Arrays.fill(distances, Integer.MAX_VALUE);

        int startIndex = getNodeIndex(a, indexedNodes);
        distances[startIndex] = 0;
        visit(startIndex, indexedNodes, predecessors, distances);

        LinkedList<Node> path = new LinkedList<Node>();
        int i = getNodeIndex(b, indexedNodes);
        while (i != startIndex) {
            if (i == -1) {
                throw new IllegalStateException("No path found");
            }
            path.addFirst(indexedNodes[i]);
            i = predecessors[i];
        }
        path.addFirst(a);
        return path;
    }

    public void visit(int nodeIndex, Node[] indexedNodes, int[] predecessors, int[] distances) {
        for (Edge edge : indexedNodes[nodeIndex].getEdges()) {
            Node child = edge.getTo();
            int childIndex = getNodeIndex(child, indexedNodes);
            int distanceToNode = distances[nodeIndex] == Integer.MAX_VALUE ? 0 : distances[nodeIndex];
            int distanceToChild = distanceToNode + edge.getWeight();
            if (distances[nodeIndex] + edge.getWeight() < distances[childIndex]) {
                distances[childIndex] = distanceToChild;
                predecessors[childIndex] = nodeIndex;
                visit(childIndex, indexedNodes, predecessors, distances);
            }
        }
    }

    public int getNodeIndex(Node node, Node[] indexedNodes) {
        for (int i = 0; i < indexedNodes.length; i++) {
            if (indexedNodes[i] == node) {
                return i;
            }
        }
        throw new IllegalStateException("Node not found in input set");
    }
}