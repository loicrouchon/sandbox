package fr.loicrouchon.visit;

import fr.loicrouchon.visit.Expression.*;

public interface ExpressionVisitor {

    public default void visit(Expression e) {
        e.accept(this);
    }

    public void visit(Variable variable);
    public void visit(And and);
    public void visit(Or or);
    public void visit(Not not);
}
