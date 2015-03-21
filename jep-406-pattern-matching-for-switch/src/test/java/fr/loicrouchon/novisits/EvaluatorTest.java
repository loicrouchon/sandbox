package fr.loicrouchon.novisits;

import static org.assertj.core.api.Assertions.assertThat;
import static fr.loicrouchon.novisits.Simplifier.*;
import static fr.loicrouchon.novisits.Expression.*;

import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.Test;

public class EvaluatorTest {

    private final Variable a = new Variable("a");
    private final Variable b = new Variable("b");
    private final Variable c = new Variable("c");

    @Test
    public void evaluateList_shouldEvaluateSimpleVariable() {
        String evaluations = Evaluator.evaluateList(
            a.and(b),
            List.of(
                Map.of("a", false, "b", false),
                Map.of("a", false, "b", true),
                Map.of("a", true, "b", false),
                Map.of("a", true, "b", true)
            )
        );
        assertThat(evaluations).isEqualTo("""
        Expression: (a AND b)
        [a:0, b:0] => 0
        [a:0, b:1] => 0
        [a:1, b:0] => 0
        [a:1, b:1] => 1
        """);
    }

    @Test
    public void evaluate_shouldEvaluateSimpleVariable() {
        Expression e = a;
        assertThat(Evaluator.evaluate(e, Map.of("a", false))).isEqualTo(false);
        assertThat(Evaluator.evaluate(e, Map.of("a", true))).isEqualTo(true);
    }

    @Test
    public void evaluate_shouldEvaluateNotExpression() {
        Expression e = not(a);
        assertThat(Evaluator.evaluate(e, Map.of("a", false))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", true))).isEqualTo(false);
    }

    @Test
    public void evaluate_shouldEvaluateAndExpression() {
        Expression e = a.and(b);
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", false))).isEqualTo(false);
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", true))).isEqualTo(false);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", false))).isEqualTo(false);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", true))).isEqualTo(true);
    }

    @Test
    public void evaluate_shouldEvaluateOrExpression() {
        Expression e = a.or(b);
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", false))).isEqualTo(false);
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", true))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", false))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", true))).isEqualTo(true);
    }

    @Test
    public void evaluate_shouldEvaluateComplexExpression() {
        Expression e = not(a.and(b.or(not(c))));
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", false, "c", false))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", false, "c", true))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", true, "c", false))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", false, "b", true, "c", true))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", false, "c", false))).isEqualTo(false);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", false, "c", true))).isEqualTo(true);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", true, "c", false))).isEqualTo(false);
        assertThat(Evaluator.evaluate(e, Map.of("a", true, "b", true, "c", true))).isEqualTo(false);
    }
}
