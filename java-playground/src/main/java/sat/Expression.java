package sat;

import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;

abstract class Expression {

    abstract void visit(ExpressionVisitor<?> visitor);

    public <T> T walk(ExpressionVisitor<T> visitor) {
        return ExpressionTreeWalker.walk(this, visitor);
    }

    public boolean evaluate(Variables variables) {
        return walk(new ExpressionEvaluatorVisitor(variables));
    }

    public Expression not() {
        return new NotExpression(this);
    }

    public Expression and(Expression exp) {
        return new AndExpression(this, exp);
    }

    public Expression or(Expression exp) {
        return new OrExpression(this, exp);
    }

    static Variable of(int id) {
        return new Variable(id);
    }

    static Expression not(Expression exp) {
        return new NotExpression(exp);
    }

    @Override
    public String toString() {
        return walk(new ToStringExpressionVisitor());
    }

    @EqualsAndHashCode(callSuper = false)
    @RequiredArgsConstructor
    public static class Variable extends Expression {

        final int id;

        @Override
        public void visit(ExpressionVisitor<?> visitor) {
            visitor.visit(this);
        }

        @Override
        public String toString() {
            return Integer.toString(id);
        }
    }

    @RequiredArgsConstructor
    public static class NotExpression extends Expression {

        final Expression exp;

        @Override
        public void visit(ExpressionVisitor<?> visitor) {
            visitor.visit(this);
        }
    }

    @RequiredArgsConstructor
    public abstract static class BinaryOperator extends Expression {

        protected final Expression left;
        protected final Expression right;
    }

    public static class AndExpression extends BinaryOperator {

        AndExpression(Expression first, Expression second) {
            super(first, second);
        }

        @Override
        public void visit(ExpressionVisitor<?> visitor) {
            visitor.visit(this);
        }
    }

    public static class OrExpression extends BinaryOperator {

        OrExpression(Expression first, Expression second) {
            super(first, second);
        }

        @Override
        public void visit(ExpressionVisitor<?> visitor) {
            visitor.visit(this);
        }
    }
}
