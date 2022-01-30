package sat;

import java.util.ArrayDeque;
import java.util.Deque;

public abstract class StackExpressionVisitor<T> implements ExpressionVisitor<T> {

    private Deque<Node> stack;

    @Override
    public Deque<Node> initialize(Expression root) {
        stack = new ArrayDeque<>();
        addFirst(root);
        return stack;
    }

    protected void addFirst(Expression exp) {
        stack.addFirst(Node.of(exp));
    }

    protected void addFirst(Runnable action) {
        stack.addFirst(Node.of(action));
    }

    protected void addLast(Expression exp) {
        stack.addLast(Node.of(exp));
    }

    protected void addLast(Runnable action) {
        stack.addFirst(Node.of(action));
    }
}
