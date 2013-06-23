package problem;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

import org.junit.Test;

import problem.TrainingProblem8.Node;

public class TrainingProblem8Test {

	@Test
	public void testEmptryTree() {
		TrainingProblem8 problem = new TrainingProblem8();
		Node root = new Node(1);
		assertThat(problem.fifthMaximumElement(root), is(1));
	}

	@Test
	public void testTreeWithLeftNodesOnly() {
		TrainingProblem8 problem = new TrainingProblem8();
		Node root = new Node(3);
		root.addLeft(new Node(2)).addLeft(new Node(1));
		assertThat(problem.fifthMaximumElement(root), is(1));
	}

	@Test
	public void testTreeWithRightNodesOnly() {
		TrainingProblem8 problem = new TrainingProblem8();
		Node root = new Node(1);
		root.addRight(new Node(2)).addRight(new Node(3));
		assertThat(problem.fifthMaximumElement(root), is(1));
	}

	@Test
	public void testTreeWithMultiplesNodes() {
		TrainingProblem8 problem = new TrainingProblem8();
		Node root = new Node(5);
		Node n3 = root.addLeft(new Node(3));
		n3.addLeft(new Node(1)).addRight(new Node(2));
		n3.addRight(new Node(4));
		root.addRight(new Node(6));
		assertThat(problem.fifthMaximumElement(root), is(2));
	}
}
