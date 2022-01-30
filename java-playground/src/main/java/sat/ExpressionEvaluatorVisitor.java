package sat;

import java.util.ArrayDeque;
import java.util.Deque;
import lombok.RequiredArgsConstructor;
import sat.Expression.AndExpression;
import sat.Expression.NotExpression;
import sat.Expression.OrExpression;
import sat.Expression.Variable;

@RequiredArgsConstructor
public class ExpressionEvaluatorVisitor extends StackExpressionVisitor<Boolean> {

    private final Variables variables;
    private Deque<Boolean> evaluationStack;

    @Override
    public Deque<Node> initialize(Expression root) {
        evaluationStack = new ArrayDeque<>();
        return super.initialize(root);
    }

    @Override
    public Boolean result() {
        assert evaluationStack.size() == 1;
        return evaluationStack.getFirst();
    }

    public void visit(Variable exp) {
        evaluationStack.addFirst(variables.get(exp.id));
    }

    public void visit(NotExpression exp) {
        addFirst(() -> evaluationStack.addFirst(not(evaluationStack)));
        addFirst(exp.exp);
    }

    public void visit(AndExpression exp) {
        addFirst(() -> evaluationStack.addFirst(and(evaluationStack)));
        addFirst(exp.right);
        addFirst(exp.left);
    }

    public void visit(OrExpression exp) {
        addFirst(() -> evaluationStack.addFirst(or(evaluationStack)));
        addFirst(exp.right);
        addFirst(exp.left);
    }

    private static Boolean and(Deque<Boolean> s) {
        Boolean second = s.removeFirst();
        Boolean first = s.removeFirst();
        return first && second;
    }

    private static Boolean or(Deque<Boolean> s) {
        Boolean second = s.removeFirst();
        Boolean first = s.removeFirst();
        return first || second;
    }

    private static Boolean not(Deque<Boolean> s) {
        return !s.removeFirst();
    }
}
