package data.binarytree;

import org.junit.Assert;
import org.junit.Test;

public class BinaryTreeTest {

	@Test
	public void testEmptyTree() {
		Node rootNode = new Node(1);
		int expected = 0;
		int actual = BinaryTree.depth(rootNode);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testLeftComputation() {
		Node rootNode = new Node(3, new Node(2, new Node(1), null), null);
		int expected = 2;
		int actual = BinaryTree.depth(rootNode);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testRightComputation() {
		Node rootNode = new Node(1, null, new Node(2, null, new Node(3)));
		int expected = 2;
		int actual = BinaryTree.depth(rootNode);
		Assert.assertEquals(expected, actual);
	}

	@Test
	public void testCompleteTreeComputation() {
		Node rootNode = new Node(4, new Node(2, new Node(1), new Node(3)), new Node(6, new Node(5), new Node(7)));
		int expected = 2;
		int actual = BinaryTree.depth(rootNode);
		Assert.assertEquals(expected, actual);
	}
	
	@Test
	public void testComputation1() {
		Node rootNode = new Node(2,
		/* left */new Node(1),
		/* right */new Node(6,
		  /* left */new Node(4,
		    /* left */new Node(3),
		    /* right */new Node(5)),
		  /* right */new Node(8,
		    /* left */new Node(7),
		    /* right */new Node(10,
		      /* left */new Node(9),
		      /* right */new Node(11)))));
		int expected = 4;
		int actual = BinaryTree.depth(rootNode);
		Assert.assertEquals(expected, actual);
	}
}
