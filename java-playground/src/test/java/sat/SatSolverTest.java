package sat;

import static org.assertj.core.api.Assertions.assertThat;
import static sat.Expression.not;
import static sat.Expression.of;
import static sat.Variables.of;

import java.util.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import sat.Expression.Variable;

class SatSolverTest {

    @Test
    void shouldEvaluate_Variable() {
        //given
        Variable v0 = of(0);
        //when//then
        assertThatEvaluatedExpressionIsCorrect(v0, of("T"), true);
        assertThatEvaluatedExpressionIsCorrect(v0, of("F"), false);
    }

    @Test
    void shouldEvaluate_NotVariable() {
        //given
        Variable v1 = of(0);
        //when//then
        assertThatEvaluatedExpressionIsCorrect(v1.not(), of("T"), false);
        assertThatEvaluatedExpressionIsCorrect(v1.not(), of("F"), true);
    }

    @Test
    void shouldEvaluate_OrExpression() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        //when//then
        assertThatEvaluatedExpressionIsCorrect(v0.or(v1), of("FF"), false);
        assertThatEvaluatedExpressionIsCorrect(v0.or(v1), of("FT"), true);
        assertThatEvaluatedExpressionIsCorrect(v0.or(v1), of("TF"), true);
        assertThatEvaluatedExpressionIsCorrect(v0.or(v1), of("TT"), true);
    }

    @Test
    void shouldEvaluate_AndExpression() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        //when//then
        assertThatEvaluatedExpressionIsCorrect(v0.and(v1), of("FF"), false);
        assertThatEvaluatedExpressionIsCorrect(v0.and(v1), of("FT"), false);
        assertThatEvaluatedExpressionIsCorrect(v0.and(v1), of("TF"), false);
        assertThatEvaluatedExpressionIsCorrect(v0.and(v1), of("TT"), true);
    }

    private void assertThatEvaluatedExpressionIsCorrect(Expression exp, Variables variables,
            boolean expectedValue) {
        assertThat(exp.evaluate(variables)).isEqualTo(expectedValue);
    }

    @Test
    void shouldSimplifyNotExpressions() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        Variable v2 = of(2);
        Variable v3 = of(3);
        //when/then
        assertThatExprIsSimplified(v0, "0");
        assertThatExprIsSimplified(v0.not(), "NOT 0");
        assertThatExprIsSimplified(v0.not().not(), "0");
        assertThatExprIsSimplified(v0.and(v1), "(0 AND 1)");
        assertThatExprIsSimplified(v0.not().and(v1.not()), "(NOT 0 AND NOT 1)");
        assertThatExprIsSimplified(v0.not().and(v1.not().not()), "(NOT 0 AND 1)");
        assertThatExprIsSimplified(not(v0.and(v1)), "(NOT 0 OR NOT 1)");
        assertThatExprIsSimplified(not(v0.and(v1.not())), "(NOT 0 OR 1)");
        assertThatExprIsSimplified(v0.or(v1), "(0 OR 1)");
        assertThatExprIsSimplified(v0.not().or(v1.not()), "(NOT 0 OR NOT 1)");
        assertThatExprIsSimplified(v0.not().or(v1.not().not()), "(NOT 0 OR 1)");
        assertThatExprIsSimplified(not(v0.or(v1)), "(NOT 0 AND NOT 1)");
        assertThatExprIsSimplified(
                not((not(v0).and(v1)).and((not(v0).or(v2)).or(v3))),
                // "NOT ((NOT v0 AND v1) AND ((NOT v0 OR v2) OR v3))",
                // "(NOT (NOT v0 AND v1) OR NOT ((NOT v0 OR v2) OR v3))",
                // "((v0 OR NOT v1) OR (NOT (NOT v0 OR v2) AND NOT v3))",
                "((0 OR NOT 1) OR ((0 AND NOT 2) AND NOT 3))"
        );
        assertThatExprIsSimplified(
                not(v0.or(v1.not().or(v2.and(v3.not().or(v0))))),
                "(NOT 0 AND (1 AND (NOT 2 OR (3 AND NOT 0))))");
    }

    @Test
    void shouldFindSolutions() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        //when/then
        assertThatSolutionIsCorrect(v0, set(of("TU")));
        assertThatSolutionIsCorrect(v0.not(), set(of("FU")));
        assertThatSolutionIsCorrect(v0.and(v0.not()), set());
        assertThatSolutionIsCorrect(v0.or(v1), set(
                of("TU"),
                of("UT")));
        assertThatSolutionIsCorrect(v0.not().or(v1), set(
                of("FU"),
                of("UT")));
        assertThatSolutionIsCorrect(v0.and(v1), set(of("TT")));
        assertThatSolutionIsCorrect(v0.not().and(v1), set(of("FT")));
    }

    private void assertThatSolutionIsCorrect(Expression exp, Set<Variables> expected) {
        Set<Variables> sol = SatSolver.solutions(exp);
        assertThat(sol).isEqualTo(expected);
    }

    @Test
    void shouldFindAllSolutions_forOrAndOr() {
        //given
        Expression v0 = of(0);
        Expression v1 = of(1);
        Expression v2 = of(2);
        Expression v3 = of(3);
        Expression exp = v0.or(v1).and(v2.or(v3));
        // when
        Set<Variables> solutions = SatSolver.solutions(exp);
        //then
        assertThat(solutions).containsExactlyInAnyOrder(
                of("TUTU"),
                of("TUUT"),
                of("UTTU"),
                of("UTUT")
        );
        for (Variables vc : solutions) {
            assertThat(exp.evaluate(vc.copy()))
                    .withFailMessage("%s is not a solution of %s", vc, exp)
                    .isTrue();
        }
    }

    @Test
    void shouldFindAllSolutions_forOrOrAndOrOr() {
        //given
        Expression v0 = of(0);
        Expression v1 = of(1);
        Expression v2 = of(2);
        Expression v3 = of(3);
        Expression v4 = of(4);
        Expression v5 = of(5);
        Expression exp = (v0.or(v1).or(v2.not())).and(v3.or(v4).or(v5).not());
        // when
        Set<Variables> solutions = SatSolver.solutions(exp);
        //then
        assertThat(solutions).containsExactlyInAnyOrder(
                of("TUUFFF"),
                of("UTUFFF"),
                of("UUFFFF")
        );
        for (Variables vc : solutions) {
            assertThat(exp.evaluate(vc.copy()))
                    .withFailMessage("%s is not a solution of %s", vc, exp)
                    .isTrue();
        }
    }

    @Test
    void shouldFindSolutions_forSomething() {
        // {conflicts: false, variables: [FUUF]} is not a solution of (NOT 0 AND NOT NOT (NOT 3 OR 2))
        //java.lang.AssertionError: {conflicts: false, variables: [FUUF]} is not a solution of (NOT 0 AND NOT NOT (NOT 3 OR 2))
        //given
        Expression v0 = of(0);
        Expression v1 = of(1);
        Expression v2 = of(2);
        Expression v3 = of(3);
        Expression exp = not(v0).and(not(not((not(v3).or(v2)))));
        // when
        Set<Variables> solutions = SatSolver.solutions(exp);
        //then
        assertThat(solutions).containsExactlyInAnyOrder(
                of("FUTU"), of("FUUF")
        );
        for (Variables vc : solutions) {
            assertThat(exp.evaluate(vc.copy()))
                    .withFailMessage("%s is not a solution of %s", vc, exp)
                    .isTrue();
        }
    }

    @Test
    @Disabled
    void shouldTestGenerateBigExpression() {
        Random random = new Random();
        int minDepth = 20;
        int maxDepth = 30;
        int nbVariables = 32;
        Expression exp = newExp(nbVariables, minDepth, maxDepth, 0, random);
        System.out.println("Validating expression " + exp);
        System.out.println("Simplified expression " + exp.walk(new SimplifyNotExpressionVisitor()));
        long solutions = 0L;
//        SolutionIterator it = new SolutionIterator(exp);
        Iterator<Variables> it = SatSolver.solutions(exp).iterator();
        while (it.hasNext()) {
            Variables vc = it.next();
            System.out.println("Validating solution " + vc);
            assertThat(exp.evaluate(vc.copy()))
                    .withFailMessage("%s is not a solution of %s", vc, exp)
                    .isTrue();
            solutions++;
        }
        System.out.printf("Found %d solutions\n", solutions);
    }

    private Expression newExp(int nbVariables, int minDepth, int maxDepth, int depth, Random random) {
        Expression newExp;
        int x;
        if (depth < minDepth) {
            x = random.nextInt(3) + 1;
        } else {
            x = random.nextInt(4);
        }
        if (depth < minDepth) {
            depth++;
        } else {
            depth += random.nextInt(maxDepth - depth + 1);
        }
        if (depth >= maxDepth || x == 0) {
            newExp = of(random.nextInt(nbVariables));
        } else if (x == 1) {
            newExp = newExp(nbVariables, minDepth, maxDepth, depth, random).not();
            //            newExp = Expression.of(0).not();
        } else if (x == 2) {
            newExp = newExp(nbVariables, minDepth, maxDepth, depth, random)
                    .and(newExp(nbVariables, minDepth, maxDepth, depth, random));
        } else {
            newExp = newExp(nbVariables, minDepth, maxDepth, depth, random)
                    .or(newExp(nbVariables, minDepth, maxDepth, depth, random));
        }
        return newExp;
    }

    @Test
    void shouldSolve_simpleVariable() {
        //given
        Variable v0 = of(0);
        //when
        Set<Variables> solution = SatSolver.solutions(v0);
        //then
        assertThat(solution).containsExactlyInAnyOrder(
                of("T"));
    }

    @Test
    void shouldSolve_NotSimpleVariable() {
        //given
        Variable v0 = of(0);
        Expression exp = not(v0);
        //when
        Set<Variables> solution = SatSolver.solutions(exp);
        //then
        assertThat(solution).containsExactlyInAnyOrder(
                of("F"));
    }

    @Test
    void shouldSolve_AndExpression() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        Expression exp = v0.and(v1);
        //when
        Set<Variables> solution = SatSolver.solutions(exp);
        //then
        assertThat(solution).containsExactlyInAnyOrder(
                of("TT"));
    }

    @Test
    void shouldSolve_AndNotExpression() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        Expression exp = not(v0).and(v1);
        //when
        Set<Variables> solution = SatSolver.solutions(exp);
        //then
        assertThat(solution).containsExactlyInAnyOrder(
                of("FT"));
    }

    @Test
    void shouldSolve_OrExpression() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        Expression exp = v0.or(v1);
        //when
        Set<Variables> solution = SatSolver.solutions(exp);
        //then
        assertThat(solution).containsExactlyInAnyOrder(
                of("TU"),
                of("UT"));
    }

    @Test
    void shouldSolve_ConstrainedOrExpression() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        Expression exp = not(v0).and(v0.or(v1));
        //when

        Set<Variables> solution = SatSolver.solutions(exp);
        //then
        assertThat(solution).containsExactlyInAnyOrder(
                of("FT"));
    }

    @Test
    void shouldReduce_SolvedExpression() {
        //given
        Variable v0 = of(0);
        Variable v1 = of(1);
        Expression exp = v0.or(v0.and(v1));
        //when
        Set<Variables> solution = SatSolver.solutions(exp);
        //then
        assertThat(solution).containsExactlyInAnyOrder(
                of("TU"));
    }

    @SafeVarargs
    private <T> Set<T> set(T... values) {
        return new HashSet<>(Arrays.asList(values));
    }

    private void assertThatExprIsSimplified(Expression exp, String expected) {
        //when
        String simplifiedExpression = exp.walk(new SimplifyNotExpressionVisitor()).toString();
        //then
        assertThat(simplifiedExpression).isEqualTo(expected);
    }
}
