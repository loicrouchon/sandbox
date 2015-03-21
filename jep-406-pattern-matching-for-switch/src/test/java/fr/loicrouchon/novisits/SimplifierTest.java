package fr.loicrouchon.novisits;

import static org.assertj.core.api.Assertions.assertThat;
import static fr.loicrouchon.novisits.Simplifier.*;
import static fr.loicrouchon.novisits.Expression.*;

import org.junit.jupiter.api.Test;

public class SimplifierTest {

    private final Variable a = new Variable("a");
    private final Variable b = new Variable("b");
    private final Variable c = new Variable("c");

    @Test
    public void simplify_shouldReturnVariableAsIs() {
        assertThat(Simplifier.simplify(a)).isEqualTo(a);
        assertThat(Simplifier.simplify(not(a))).isEqualTo(not(a));
    }

    @Test
    public void simplify_shouldEliminateDoubleConsecutiveNot() {
        assertThat(Simplifier.simplify(not(not(a)))).isEqualTo(a);
        assertThat(Simplifier.simplify(not(not(a.and(b))))).isEqualTo(a.and(b));
        assertThat(Simplifier.simplify(not(not(a.or(b))))).isEqualTo(a.or(b));
    }

    @Test
    public void simplify_shouldReturnExpressionAsIs() {
        assertThat(Simplifier.simplify(a.and(b))).isEqualTo(a.and(b));
        assertThat(Simplifier.simplify(a.or(b))).isEqualTo(a.or(b));
    }

    @Test
    public void simplify_shouldPushNotInsideExpressions() {
        assertThat(Simplifier.simplify(not(a.and(b)))).isEqualTo(not(a).or(not(b)));
        assertThat(Simplifier.simplify(not(a.or(b)))).isEqualTo(not(a).and(not(b)));
    }

    @Test
    public void simplify_shouldSimplifyComplexExpressions() {
        assertThat(Simplifier.simplify(not(a.and(b.or(not(c)))))).isEqualTo(not(a).or(not(b).and(c)));
    }
}
