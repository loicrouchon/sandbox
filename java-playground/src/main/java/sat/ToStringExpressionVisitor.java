package sat;

import java.util.Deque;
import lombok.Value;
import sat.Expression.AndExpression;
import sat.Expression.NotExpression;
import sat.Expression.OrExpression;
import sat.Expression.Variable;

public class ToStringExpressionVisitor extends StackExpressionVisitor<String> {

    private StringBuilder sb;

    @Override
    public Deque<Node> initialize(Expression root) {
        sb = new StringBuilder();
        return super.initialize(root);
    }

    @Override
    public String result() {
        return sb.toString();
    }

    public void visit(Variable exp) {
        sb.append(exp.id);
    }

    public void visit(NotExpression exp) {
        sb.append("NOT ");
        addFirst(exp.exp);
    }

    public void visit(AndExpression exp) {
        addFirst(new StringAction(sb, ")"));
        addFirst(exp.right);
        addFirst(new StringAction(sb, " AND "));
        addFirst(exp.left);
        sb.append("(");
    }

    public void visit(OrExpression exp) {
        addFirst(new StringAction(sb, ")"));
        addFirst(exp.right);
        addFirst(new StringAction(sb, " OR "));
        addFirst(exp.left);
        sb.append("(");
    }

    @Value
    static class StringAction implements Runnable {

        StringBuilder sb;
        String value;

        @Override
        public void run() {
            sb.append(value);
        }
    }
}
