package problem;

/**
 * 
 * How do you find out the fifth maximum element in an Binary Search Tree in
 * efficient manner.
 * 
 * Note: You should not use use any extra space. i.e sorting Binary Search Tree
 * and storing the
 * 
 * results in an array and listing out the fifth element.
 */
public class TrainingProblem8 {

	public static final class Node {

		public final int data;
		public Node parent;
		public Node left;
		public Node right;

		public Node(int data) {
			this.data = data;
		}

		public Node addLeft(Node left) {
			this.left = left;
			left.parent = this;
			return left;
		}

		public Node addRight(Node right) {
			this.right = right;
			right.parent = this;
			return right;
		}
	}

	public int fifthMaximumElement(Node root) {
		int result = root.data;
		int nbMaxElt = 1;
		Node node = root;
		Node previous = root;
		while (node.right != null) {
			previous = node;
			node = node.right;
		}
		while (nbMaxElt < 5
		        && !(node == root && ((node.left == null && node.right == null) || previous == node.left || (node.left == null && previous == node.right)))) {
			if (node.right != null && previous != node.right && previous != node.left) {
				previous = node;
				node = node.right;
				nbMaxElt++;
				result = node.data;
			} else if (node.left != null && previous != node.left) {
				previous = node;
				node = node.left;
				nbMaxElt++;
				result = node.data;
			} else {
				previous = node;
				node = node.parent;
			}
		}
		return result;
	}
}