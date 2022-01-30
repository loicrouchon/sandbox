package sat;

import java.util.*;

public class BottomUpSolutionVisitor extends StackExpressionVisitor<Set<Variables>> {

    private ArrayDeque<Set<Variables>> solutions;

    @Override
    public Deque<Node> initialize(Expression root) {
        solutions = new ArrayDeque<>();
        return super.initialize(root.walk(new SimplifyNotExpressionVisitor()));
    }

    @Override
    public Set<Variables> result() {
        assert solutions.size() == 1;
        return solutions.getFirst();
    }

    public void visit(Expression.Variable exp) {
        Variables variables = Variables.init();
        variables.set(exp.id, true);
        solutions.addFirst(Collections.singleton(variables));
    }

    public void visit(Expression.NotExpression exp) {
        if (exp.exp instanceof Expression.Variable) {
            Variables variables = Variables.init();
            variables.set(((Expression.Variable) exp.exp).id, false);
            solutions.addFirst(Collections.singleton(variables));
        } else {
            throw new UnsupportedOperationException();
        }
    }

    public void visit(Expression.AndExpression exp) {
        addFirst(this::and);
        addFirst(exp.right);
        addFirst(exp.left);
    }

    public void visit(Expression.OrExpression exp) {
        addFirst(this::or);
        addFirst(exp.right);
        addFirst(exp.left);
    }

    private void and() {
        Set<Variables> second = solutions.removeFirst();
        Set<Variables> first = solutions.removeFirst();
        Set<Variables> variablesSet = new HashSet<>();
        for (Variables a : first) {
            for (Variables b : second) {
                Variables v = a.merge(b);
                if (!v.hasConflicts()) {
                    variablesSet.add(v);
                }
            }
        }
        solutions.addFirst(reduce(variablesSet));
    }

    private void or() {
        Set<Variables> second = solutions.removeFirst();
        Set<Variables> first = solutions.removeFirst();
        Set<Variables> variablesSet = new HashSet<>(first);
        variablesSet.addAll(second);
        solutions.addFirst(reduce(variablesSet));
    }

    private Set<Variables> reduce(Set<Variables> variablesSet) {
        if (variablesSet.isEmpty()) {
            return variablesSet;
        }
        Variables[] variables = variablesSet.stream()
                .sorted(Comparator.comparing(Variables::ordinal))
                .toArray(Variables[]::new);
        Set<Variables> reduced = new HashSet<>();
        reduced.add(variables[0]);
        for (int i = 1; i < variables.length; i++) {
            boolean isASuperSet = false;
            Variables v = variables[i];
            for (Variables rv : reduced) {
                if (v.isSuperSetOf(rv)) {
                    isASuperSet = true;
                    break;
                }
            }
            if (!isASuperSet) {
                reduced.add(v);
            }
        }
        return reduced;
    }
}
