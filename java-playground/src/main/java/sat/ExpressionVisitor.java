package sat;

import java.util.Deque;

public interface ExpressionVisitor<T> {

    Deque<Node> initialize(Expression root);

    T result();

    void visit(Expression.Variable exp);

    void visit(Expression.NotExpression exp);

    void visit(Expression.AndExpression exp);

    void visit(Expression.OrExpression exp);
}
