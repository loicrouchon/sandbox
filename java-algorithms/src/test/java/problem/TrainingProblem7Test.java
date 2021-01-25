package problem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import problem.TrainingProblem7.Node;

public class TrainingProblem7Test {

	@Test
	public void testEmptryTree() {
		TrainingProblem7 problem = new TrainingProblem7();
		Node root = new Node(1);
		assertThat(problem.depth(root), is(0));
	}

	@Test
	public void testTreeWithLeftNodesOnly() {
		TrainingProblem7 problem = new TrainingProblem7();
		Node root = new Node(3);
		root.addLeft(new Node(2)).addLeft(new Node(1));
		assertThat(problem.depth(root), is(2));
	}

	@Test
	public void testTreeWithRightNodesOnly() {
		TrainingProblem7 problem = new TrainingProblem7();
		Node root = new Node(1);
		root.addRight(new Node(2)).addRight(new Node(3));
		assertThat(problem.depth(root), is(2));
	}

	@Test
	public void testTreeWithMultiplesNodes() {
		TrainingProblem7 problem = new TrainingProblem7();
		Node root = new Node(5);
		Node n3 = root.addLeft(new Node(3));
		n3.addLeft(new Node(1)).addRight(new Node(2));
		n3.addRight(new Node(4));
		root.addRight(new Node(6));
		assertThat(problem.depth(root), is(3));
	}
}
