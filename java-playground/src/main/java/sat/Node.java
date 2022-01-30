package sat;

import lombok.Value;

@Value
public class Node {

    Expression exp;
    Runnable action;

    public static Node of(Runnable action) {
        return new Node(null, action);
    }

    public static Node of(Expression exp) {
        return new Node(exp, null);
    }

    public void visit(ExpressionVisitor<?> visitor) {
        if (action != null) {
            action.run();
        } else if (exp != null) {
            exp.visit(visitor);
        } else {
            throw new IllegalStateException("No action nor expression");
        }
    }
}
