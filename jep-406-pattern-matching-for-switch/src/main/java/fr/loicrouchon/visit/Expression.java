package fr.loicrouchon.visit;

import fr.loicrouchon.visit.Expression.*;

public interface Expression {

    void accept(ExpressionVisitor visitor);

    default Expression not() {
        return new Not(this);
    }

    default Expression and(Expression other) {
        return new And(this, other);
    }

    default Expression or(Expression other) {
        return new Or(this, other);
    }

    static Expression not(Expression expression) {
        return expression.not();
    }

    record Variable(String name) implements Expression {

        public void accept(ExpressionVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String toString() {
            return name;
        }
    }

    record And(Expression left, Expression right) implements Expression {

        public void accept(ExpressionVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String toString() {
            return String.format("(%s AND %s)", left, right);
        }
    }

    record Or(Expression left, Expression right) implements Expression {

        public void accept(ExpressionVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String toString() {
            return String.format("(%s OR %s)", left, right);
        }
    }

    record Not(Expression expression) implements Expression {

        public void accept(ExpressionVisitor visitor) {
            visitor.visit(this);
        }

        @Override
        public String toString() {
            return "!" + expression;
        }
    }
}
