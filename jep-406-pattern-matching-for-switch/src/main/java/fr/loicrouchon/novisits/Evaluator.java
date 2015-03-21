package fr.loicrouchon.novisits;

import fr.loicrouchon.novisits.Expression.*;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Evaluator {

    public static boolean evaluate(Expression expression, Map<String, Boolean> variables) {
        return switch (expression) {
            case Variable v   -> variables.get(v.name());
            case And      and -> evaluate(and.left(), variables) && evaluate(and.right(), variables);
            case Or       or  -> evaluate(or.left(), variables) || evaluate(or.right(), variables);
            case Not      not -> !evaluate(not.expression(), variables);
        };
    }

    public static String evaluateList(Expression expression, List<Map<String, Boolean>> variables) {
        record EvaluationResult(Map<String, Boolean> vars, boolean result) {
            @Override
            public String toString() {
                return String.format("%s => %s",
                    vars.entrySet()
                    .stream()
                    .sorted(Comparator.comparing(Map.Entry::getKey))
                    .map(e -> String.format("%s:%s", e.getKey(), bool(e.getValue())))
                    .collect(Collectors.toList()),
                    bool(result));
            }
        }
        return String.format("""
                Expression: %s
                %s
                """,
                expression,
                variables.stream()
                    .map(vars -> new EvaluationResult(vars, evaluate(expression, vars)).toString())
                    .collect(Collectors.joining("\n")));
    }

    private static String bool(boolean b) {
        return b ? "1" : "0";
    }
}
