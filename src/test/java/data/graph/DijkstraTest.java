package data.graph;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

import data.graph.Dijkstra.Node;

public class DijkstraTest {

    private Dijkstra algorithm;

    @Before
    public void setUp() {
        algorithm = new Dijkstra();
    }

    @Test
    public void testAlreadyArrived() {
        Set<Node> nodes = new HashSet<Node>();
        Node a = new Node(1);
        nodes.add(a);
        List<Node> shortestPath = algorithm.shortestPath(nodes, a, a);
        List<Node> expected = Arrays.asList(a);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testDirectConnection() {
        Set<Node> nodes = new HashSet<Node>();
        Node a = new Node(1);
        nodes.add(a);
        Node b = new Node(2);
        nodes.add(b);
        a.connectTo(b, 100);
        List<Node> shortestPath = algorithm.shortestPath(nodes, a, b);
        List<Node> expected = Arrays.asList(a, b);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testDirectConnectionWithMoreConnections1() {
        Set<Node> nodes = new HashSet<Node>();
        Node a = new Node(1);
        nodes.add(a);
        Node b = new Node(2);
        nodes.add(b);
        Node c = new Node(3);
        nodes.add(c);
        a.connectTo(b, 100);
        a.connectTo(a, 50);
        a.connectTo(c, 70);
        b.connectTo(c, 25);
        c.connectTo(a, 95);
        c.connectTo(b, 50);
        List<Node> shortestPath = algorithm.shortestPath(nodes, a, b);
        List<Node> expected = Arrays.asList(a, b);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testDirectConnectionWithMoreConnections2() {
        Set<Node> nodes = new HashSet<Node>();
        Node a = new Node(1);
        nodes.add(a);
        Node b = new Node(2);
        nodes.add(b);
        Node c = new Node(3);
        nodes.add(c);
        a.connectTo(b, 100);
        a.connectTo(a, 50);
        a.connectTo(c, 70);
        b.connectTo(c, 25);
        c.connectTo(a, 95);
        c.connectTo(b, 10);
        List<Node> shortestPath = algorithm.shortestPath(nodes, a, b);
        List<Node> expected = Arrays.asList(a, c, b);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testComplexExample() {
        Set<Node> nodes = new HashSet<Node>();
        Node a = new Node(1);
        Node b = new Node(2);
        Node c = new Node(3);
        Node d = new Node(4);
        Node e = new Node(5);
        Node f = new Node(6);
        Node g = new Node(7);
        Node h = new Node(6);
        Node i = new Node(9);
        Node j = new Node(10);
        nodes.addAll(Arrays.asList(a, b, c, d, e, f, g, h, i, j));
        connect(a, b, 85);
        connect(a, c, 217);
        connect(a, e, 173);
        connect(b, f, 80);
        connect(c, g, 186);
        connect(c, h, 103);
        connect(e, j, 502);
        connect(f, i, 250);
        connect(i, j, 84);
        connect(h, d, 183);
        connect(h, j, 167);
        List<Node> shortestPath = algorithm.shortestPath(nodes, a, j);
        List<Node> expected = Arrays.asList(a, c, h, j);
        assertEquals(expected, shortestPath);
    }

    private void connect(Node a, Node b, int weight) {
        a.connectTo(b, weight);
        b.connectTo(a, weight);
    }
}
