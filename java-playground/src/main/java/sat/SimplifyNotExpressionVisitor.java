package sat;

import java.util.ArrayDeque;
import java.util.Deque;
import lombok.RequiredArgsConstructor;
import sat.Expression.*;

@RequiredArgsConstructor
public class SimplifyNotExpressionVisitor extends StackExpressionVisitor<Expression> {

    private Deque<Expression> exprStack;

    @Override
    public Deque<Node> initialize(Expression root) {
        exprStack = new ArrayDeque<>();
        return super.initialize(root);
    }

    @Override
    public Expression result() {
        assert exprStack.size() == 1;
        return exprStack.getFirst();
    }

    public void visit(Variable exp) {
        exprStack.addFirst(exp);
    }

    public void visit(NotExpression exp) {
        if (exp.exp instanceof NotExpression) {
            addFirst(((NotExpression) (exp.exp)).exp);
        } else if (exp.exp instanceof Variable) {
            exprStack.addFirst(exp.exp.not());
        } else if (exp.exp instanceof AndExpression) {
            addFirst(() -> or(exprStack));
            addFirst(((BinaryOperator) exp.exp).right.not());
            addFirst(((BinaryOperator) exp.exp).left.not());
        } else if (exp.exp instanceof OrExpression) {
            addFirst(() -> and(exprStack));
            addFirst(((BinaryOperator) exp.exp).right.not());
            addFirst(((BinaryOperator) exp.exp).left.not());
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void visit(AndExpression exp) {
        addFirst(() -> and(exprStack));
        addFirst(exp.right);
        addFirst(exp.left);
    }

    public void visit(OrExpression exp) {
        addFirst(() -> or(exprStack));
        addFirst(exp.right);
        addFirst(exp.left);
    }

    private static void and(Deque<Expression> s) {
        Expression second = s.removeFirst();
        Expression first = s.removeFirst();
        s.addFirst(first.and(second));
    }

    private static void or(Deque<Expression> s) {
        Expression second = s.removeFirst();
        Expression first = s.removeFirst();
        s.addFirst(first.or(second));
    }
}
