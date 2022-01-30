package sat;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.Iterator;
import java.util.NoSuchElementException;
import lombok.RequiredArgsConstructor;
import lombok.Value;
import sat.Expression.AndExpression;
import sat.Expression.NotExpression;
import sat.Expression.OrExpression;
import sat.Expression.Variable;

public class SolutionIterator implements Iterator<Variables> {

    @Value(staticConstructor = "of")
    private static class ExpressionContext {

        Expression exp;
        boolean expectedValue;
        boolean branchAlternative;

        public static ExpressionContext copy(Expression exp, boolean expectedValue) {
            return of(exp, expectedValue, true);
        }

        public static ExpressionContext of(Expression exp, boolean expectedValue) {
            return of(exp, expectedValue, false);
        }
    }

    @RequiredArgsConstructor
    private static class Alternative {

        private final Variables vc;
        private final Deque<ExpressionContext> stack;

        public Alternative copy() {
            ArrayDeque<ExpressionContext> newStack = new ArrayDeque<>(stack);
            newStack.removeFirst(); // remove the other part of the alternative
            return new Alternative(vc.copy(), newStack);
        }

        public static Alternative of(Expression exp) {
            Deque<ExpressionContext> stack = new ArrayDeque<>();
            stack.addFirst(ExpressionContext.of(exp, true));
            return new Alternative(Variables.init(), stack);
        }
    }

    private long alternativeExplored = 0L;

    private final Deque<Alternative> alternatives;

    private Variables next;

    public SolutionIterator(Expression exp) {
        alternatives = new ArrayDeque<>();
        alternatives.add(Alternative.of(exp));
    }

    @Override
    public boolean hasNext() {
        if (next == null) {
            next = computeNext();
        }
        return next != null;
    }

    @Override
    public Variables next() {
        if (next == null) {
            throw new NoSuchElementException();
        }
        Variables current = next;
        next = null;
        return current;
    }

    // TODO bottom-up algorithm:
    //   algorithm 1:
    //    - Push negation to the variables
    //    - Start from leaves and bubble up Variables to make it true
    //    - On OR, Merge the Variables sets (simplify if one is a subset of the other)
    //    - On AND, Merge the Variables sets (apply constraints to all
    //             => not feasible (cartesian product))
    //   algorithm 2:
    //    - Push negation to the variables
    //    - Analyse values of all leaves (Variables / Not(Variables))
    //    - Set obvious values
    //    - Simplify by removing branches with those values.
    //    - run until no changes => apply another algorithm.

    private Variables computeNext() {
        while (!alternatives.isEmpty()) {
            Alternative alternative = alternatives.removeFirst();
            // TODO migrate Alternative iterator to a visitor
            while (!alternative.stack.isEmpty() && !alternative.vc.hasConflicts()) {
                ExpressionContext ec = alternative.stack.removeFirst();
                if (ec.branchAlternative) {
                    Alternative newAlternative = alternative.copy();
                    newAlternative.stack.addLast(ExpressionContext.of(ec.exp, ec.expectedValue));
                    alternatives.addFirst(newAlternative);
                } else if (ec.exp instanceof Variable) {
                    alternative.vc.set(((Variable) ec.exp).id, ec.expectedValue);
                } else if (ec.exp instanceof NotExpression) {
                    NotExpression not = (NotExpression) ec.exp;
                    alternative.stack.addFirst(ExpressionContext.of(not.exp, !ec.expectedValue));
                } else if (ec.exp instanceof AndExpression) {
                    AndExpression and = (AndExpression) ec.exp;
                    if (ec.expectedValue) {
                        andStack(alternative, and.left, and.right, true);
                    } else {
                        orBranch(alternative, and.left, and.right, false);
                    }
                } else if (ec.exp instanceof OrExpression) {
                    OrExpression or = (OrExpression) ec.exp;
                    if (ec.expectedValue) {
                        orBranch(alternative, or.left, or.right, true);
                    } else {
                        andStack(alternative, or.left, or.right, false);
                    }
                }
            }
            alternativeExplored++;
            if (!alternative.vc.hasConflicts()) {
                return alternative.vc;
            }
        }
        return null;
    }

    private void andStack(Alternative alternative, Expression left, Expression right, boolean expectedValue) {
        alternative.stack.addFirst(ExpressionContext.of(right, expectedValue));
        alternative.stack.addFirst(ExpressionContext.of(left, expectedValue));
    }

    private void orBranch(Alternative alternative, Expression left, Expression right,
            boolean expectedValue) {
        // TODO do not copy here but create a copyable element and push it to the stack
        //    The copy will only copy the variable constraints, not the stack itself as the stack
        //    at this point should only contain it + the other branch of the or
        //    (this should be checked with a runtime assertion)
        //    needs to be on top of the stack (on top of the left one)
        //            Alternative newAlternative = alternative.copy();
        //            newAlternative.stack.addLast(ExpressionContext.copy(alternative, right, expectedValue));
        //            alternatives.addLast(newAlternative);
        alternative.stack.addLast(ExpressionContext.copy(right, expectedValue));
        alternative.stack.addLast(ExpressionContext.of(left, expectedValue));
    }
}
