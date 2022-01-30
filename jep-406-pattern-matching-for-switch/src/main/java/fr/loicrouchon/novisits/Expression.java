package fr.loicrouchon.novisits;

import fr.loicrouchon.novisits.Expression.*;

public sealed interface Expression permits Variable,And,Or,Not {

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
        @Override
        public String toString() {
            return name;
        }
    }

    record And(Expression left, Expression right) implements Expression {
        @Override
        public String toString() {
            return String.format("(%s AND %s)", left, right);
        }
    }

    record Or(Expression left, Expression right) implements Expression {
        @Override
        public String toString() {
            return String.format("(%s OR %s)", left, right);
        }
    }

    record Not(Expression expression) implements Expression {
        @Override
        public String toString() {
            return "!" + expression;
        }
    }
}
