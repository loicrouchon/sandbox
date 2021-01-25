package data.binarytree;

public class RedBlackTree {

    private enum Color {
        RED, BLACK;
    }

    private static class Node {
        Integer key;
        Node parent = NIL;
        Node left = NIL;
        Node right = NIL;
        Color color = Color.RED;

        public Node(Integer key) {
            this.key = key;
        }

        @Override
        public String toString() {
            if (this == NIL) {
                return "{NIL}";
            }
            return "{key=" + key + ",color=" + color + ",left=" + left + ",right=" + right + "}";
        }
    }

    private static final Node NIL = new Node(null);
    static {
        NIL.color = Color.BLACK;
    }

    private Node root = NIL;

    private int rotationCounter = 0;

    public int getRotationCounter() {
        return rotationCounter;
    }

    public void resetRotationCounter() {
        rotationCounter = 0;
    }

    public void insert(int key) {
        Node previousNode = NIL;
        Node node = root;
        while (node != NIL) {
            previousNode = node;
            if (key < node.key) {
                node = node.left;
            } else {
                node = node.right;
            }
        }
        Node newNode = new Node(key);
        newNode.parent = previousNode;
        if (previousNode == NIL) {
            root = newNode;
        } else {
            if (key < previousNode.key) {
                previousNode.left = newNode;
            } else {
                previousNode.right = newNode;
            }
        }
        fix(newNode);
    }

    public void fix(Node node) {
        while (node.parent.color == Color.RED) {
            if (node.parent == node.parent.parent.left) {
                Node uncle = node.parent.parent.right;
                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.right) {
                        node = node.parent;
                        leftRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    rightRotate(node.parent.parent);
                    node = node.parent;
                }
            } else {
                Node uncle = node.parent.parent.left;
                if (uncle.color == Color.RED) {
                    node.parent.color = Color.BLACK;
                    uncle.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    node = node.parent.parent;
                } else {
                    if (node == node.parent.left) {
                        node = node.parent;
                        rightRotate(node);
                    }
                    node.parent.color = Color.BLACK;
                    node.parent.parent.color = Color.RED;
                    leftRotate(node.parent.parent);
                    node = node.parent;
                }
            }
        }
        root.color = Color.BLACK;
    }

    public void leftRotate(Node x) {
        rotationCounter++;
        Node y = x.right;
        if (x.parent != NIL) {
            if (x.parent.left == x) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        } else {
            root = y;
        }
        y.parent = x.parent;
        x.parent = y;
        x.right = y.left;
        x.right.parent = x;
        y.left = x;
        y.left.parent = y;
    }

    public void rightRotate(Node x) {
        rotationCounter++;
        Node y = x.left;
        if (x.parent != NIL) {
            if (x.parent.left == x) {
                x.parent.left = y;
            } else {
                x.parent.right = y;
            }
        } else {
            root = y;
        }
        y.parent = x.parent;
        x.parent = y;
        x.left = y.right;
        x.left.parent = x;
        y.right = x;
        y.right.parent = y;
    }

    @Override
    public String toString() {
        return root.toString();
    }
}