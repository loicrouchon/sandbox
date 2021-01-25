package data.binarytree;

public class Node {

	private int key;
	private Node left;
	private Node right;
	private Node parent;

	public Node(int key) {
		this(key, null, null);
	}

	public Node(int key, Node left, Node right) {
		setKey(key);
		setLeft(left);
		setRight(right);
	}

	public int getKey() {
		return key;
	}

	public void setKey(int key) {
		this.key = key;
	}

	public Node getLeft() {
		return left;
	}

	public void setLeft(Node left) {
		this.left = left;
		if (left != null) {
			left.parent = this;
		}
	}

	public Node getRight() {
		return right;
	}

	public void setRight(Node right) {
		this.right = right;
		if (right != null) {
			right.parent = this;
		}
	}

	public Node getParent() {
		return parent;
	}

	@Override
	public String toString() {
		return "{\"key\":" + key + ",\"left\":" + left + ",\"right\":" + right + "}";
	}
}
