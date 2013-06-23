package problem;

/**
 * 
 * Write a program to find depth of binary search tree without using recursion
 */
public class TrainingProblem7 {

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

	public int depth(Node root) {
		int maxDepth = 0;
		int depth = 0;
		Node node = root;
		Node previous = root;
		while (!(node == root && ((root.left == null && root.right == null) || (previous == root.right) || (root.right == null && previous == root.left)))) {
			if (node.left != null && previous != node.left && (previous != node.right)) {
				previous = node;
				node = node.left;
				depth++;
			} else if (node.right != null && previous != node.right) {
				previous = node;
				node = node.right;
				depth++;
			} else {
				previous = node;
				node = node.parent;
				depth--;
			}
			if (depth > maxDepth) {
				maxDepth = depth;
			}
		}
		return maxDepth;
	}
}