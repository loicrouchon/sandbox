package fr.loicrouchon.visit;

import static fr.loicrouchon.visit.Expression.*;

import fr.loicrouchon.visit.Expression.*;

public class Simplifier {

    public static Expression simplify(Expression e) {
        var visitor = new SimplifierVisitor();
        visitor.visit(e);
        return visitor.getExpression();
    }

    private static class SimplifierVisitor implements ExpressionVisitor {

        private Expression expression;

        public Expression getExpression() {
            return expression;
        }

        public void visit(Variable variable) {
            expression = variable;
        }

        public void visit(And and) {
            var leftVisitor = new SimplifierVisitor();
            var rightVisitor = new SimplifierVisitor();
            and.left().accept(leftVisitor);
            and.right().accept(rightVisitor);
            expression = leftVisitor.getExpression().and(rightVisitor.getExpression());
        }
    
        public void visit(Or or) {
            var leftVisitor = new SimplifierVisitor();
            var rightVisitor = new SimplifierVisitor();
            or.left().accept(leftVisitor);
            or.right().accept(rightVisitor);
            expression = leftVisitor.getExpression().or(rightVisitor.getExpression());
        }

        public void visit(Not not) {
            var notVisitor = new SimplifierNotVisitor();
            notVisitor.visit(not.expression());
            expression = notVisitor.getExpression();
        }

    }

    private static class SimplifierNotVisitor implements ExpressionVisitor {

        private Expression expression;

        public Expression getExpression() {
            return expression;
        }

        public void visit(Variable variable) {
            expression = not(variable);
        }

        public void visit(And and) {
            var leftVisitor = new SimplifierVisitor();
            var rightVisitor = new SimplifierVisitor();
            not(and.left()).accept(leftVisitor);
            not(and.right()).accept(rightVisitor);
            expression = leftVisitor.getExpression().or(rightVisitor.getExpression());
        }
    
        public void visit(Or or) {
            var leftVisitor = new SimplifierVisitor();
            var rightVisitor = new SimplifierVisitor();
            not(or.left()).accept(leftVisitor);
            not(or.right()).accept(rightVisitor);
            expression = leftVisitor.getExpression().and(rightVisitor.getExpression());
        }

        public void visit(Not not) {
            expression = not.expression();
        }
    }
}
