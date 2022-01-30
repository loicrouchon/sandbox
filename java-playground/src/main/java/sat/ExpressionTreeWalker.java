package sat;

import java.util.Deque;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ExpressionTreeWalker {

    public static <T> T walk(Expression root, ExpressionVisitor<T> visitor) {
        Deque<Node> stack = visitor.initialize(root);
        while (!stack.isEmpty()) {
            Node node = stack.removeFirst();
            node.visit(visitor);
        }
        return visitor.result();
    }
}
