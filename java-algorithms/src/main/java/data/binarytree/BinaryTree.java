package data.binarytree;

public class BinaryTree {

	public static int depth(Node rootNode) {
		int depth = 0;
		int currentDepth = 0;
		Node currentNode = rootNode;
		Node previousNode = null;
		while (hasChildren(currentNode)) {
			currentDepth++;
			if (currentDepth > depth) {
				depth = currentDepth;
			}
			Node left = currentNode.getLeft();
			Node right = currentNode.getRight();
			if (hasChildren(left) && left != previousNode && (right == null || right != previousNode)) {
				previousNode = currentNode;
				currentNode = left;
			} else if (hasChildren(right) && right != previousNode) {
				previousNode = currentNode;
				currentNode = right;
			} else if (currentNode != rootNode) {
				previousNode = currentNode;
				currentNode = currentNode.getParent();
				currentDepth -= 2;
			} else {
				currentNode = null;
			}
		}
		return depth;
	}

	private static boolean hasChildren(Node node) {
		return node != null && (node.getLeft() != null || node.getRight() != null);
	}

}
