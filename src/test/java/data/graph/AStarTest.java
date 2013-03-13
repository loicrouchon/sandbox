package data.graph;

import static org.junit.Assert.assertEquals;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import data.graph.AStar.Node;

public class AStarTest {

    private AStar algorithm;

    @Before
    public void setUp() {
        algorithm = new AStar();
    }

    @Test
    public void testAlreadyArrived() {

        Node a = new Node(1, 10, 15);
        List<Node> nodes = Arrays.asList(a);
        List<Node> shortestPath = algorithm.shortestPath(toArray(nodes), 0, 0);
        List<Node> expected = Arrays.asList(a);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testDirectConnection() {
        Node a = new Node(1, 10, 15);
        Node b = new Node(2, 10, 20);
        a.connectTo(b);
        List<Node> nodes = Arrays.asList(a, b);
        List<Node> shortestPath = algorithm.shortestPath(toArray(nodes), 0, 1);
        List<Node> expected = Arrays.asList(a, b);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testDirectConnectionWithMoreConnections1() {
        Node a = new Node(1, 10, 15);
        Node b = new Node(2, 10, 20);
        Node c = new Node(3, 11, 18);
        a.connectTo(b);
        a.connectTo(a);
        a.connectTo(c);
        b.connectTo(c);
        c.connectTo(a);
        c.connectTo(b);
        List<Node> nodes = Arrays.asList(a, b, c);
        List<Node> shortestPath = algorithm.shortestPath(toArray(nodes), 0, 1);
        List<Node> expected = Arrays.asList(a, b);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testDirectConnectionWithMoreConnections2() {
        Node a = new Node(1, 10, 15);
        Node b = new Node(2, 10, 20);
        Node c = new Node(3, 10, 17);
        a.connectTo(b);
        a.connectTo(a);
        a.connectTo(c);
        b.connectTo(c);
        c.connectTo(a);
        c.connectTo(b);
        List<Node> nodes = Arrays.asList(a, b, c);
        List<Node> shortestPath = algorithm.shortestPath(toArray(nodes), 0, 1);
        List<Node> expected = Arrays.asList(a, b);
        assertEquals(expected, shortestPath);
    }

    @Test
    public void testComplexExample1() {
        Node a = new Node(1, 10, 15);
        Node b = new Node(2, 12, 16);
        Node c = new Node(3, 17, 20);
        Node d = new Node(4, 13, 18);
        Node e = new Node(5, 18, 16);
        Node f = new Node(6, 20, 20);
        Node g = new Node(7, 14, 19);
        Node h = new Node(6, 18, 19);
        Node i = new Node(9, 19, 17);
        Node j = new Node(10, 20, 18);
        connect(a, b);
        connect(a, c);
        connect(a, e);
        connect(b, f);
        connect(c, g);
        connect(c, h);
        connect(e, j);
        connect(f, i);
        connect(i, j);
        connect(h, d);
        connect(h, j);
        List<Node> nodes = Arrays.asList(a, b, c, d, e, f, g, h, i, j);
        List<Node> shortestPath = algorithm.shortestPath(toArray(nodes), 0, 3);
        List<Node> expected = Arrays.asList(a, c, h, d);
        assertEquals(expected, shortestPath);
    }

    public void testComplexExample2() {
        Node a = new Node(1, 10, 15);
        Node b = new Node(2, 12, 16);
        Node c = new Node(3, 17, 20);
        Node d = new Node(4, 13, 18);
        Node e = new Node(5, 18, 16);
        Node f = new Node(6, 20, 20);
        Node g = new Node(7, 14, 19);
        Node h = new Node(6, 18, 19);
        Node i = new Node(9, 19, 17);
        Node j = new Node(10, 20, 18);
        connect(a, b);
        connect(a, c);
        connect(a, e);
        connect(b, f);
        connect(c, g);
        connect(c, h);
        connect(e, j);
        connect(f, i);
        connect(i, j);
        connect(h, d);
        connect(h, j);
        List<Node> nodes = Arrays.asList(a, b, c, d, e, f, g, h, i, j);
        List<Node> shortestPath = algorithm.shortestPath(toArray(nodes), 0, 8);
        List<Node> expected = Arrays.asList(a, e, j, i);
        assertEquals(expected, shortestPath);
    }

    private void connect(Node a, Node b) {
        a.connectTo(b);
        b.connectTo(a);
    }

    private Node[] toArray(List<Node> nodes) {
        return nodes.toArray(new Node[nodes.size()]);
    }
}
