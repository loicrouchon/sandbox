package sat;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class SatSolver {

    public static Set<Variables> solutions(Expression exp) {
        return solutionsUsingBottomUpApproach(exp);
    }

    public static Set<Variables> solutionsUsingBottomUpApproach(Expression exp) {
        return exp.walk(new BottomUpSolutionVisitor());
    }

    public static Set<Variables> solutionsUsingTopDownApproach(Expression exp) {
        Set<Variables> solutions = new HashSet<>();
        SolutionIterator it = new SolutionIterator(exp);
        while (it.hasNext()) {
            solutions.add(it.next());
        }
        return solutions;
    }

    public static Optional<Variables> findFirstSolution(Expression exp) {
        SolutionIterator it = new SolutionIterator(exp);
        if (it.hasNext()) {
            return Optional.of(it.next());
        }
        return Optional.empty();
    }
}
