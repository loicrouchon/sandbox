package fr.loicrouchon.novisits;

import fr.loicrouchon.novisits.Expression.*;

public class Simplifier {

    /**
     * <p>Takes an {@link Expression} and builds an other {@link Expression} where the {@link Not}
     * are pushed to {@link Variable} leaves.
     * For example:
     * <ul>
     *   <li>{@code NOT(A AND B) => (NOT A) OR (NOT B)}</li>
     *   <li>{@code NOT(A OR B) => (NOT A) AND (NOT B)}</li>
     * </ul>
     * </p>
     * <p>When two {@link Not} are consecutive, they are eliminated.
     * For example: {@code NOT(NOT(A)) => A}</p>
     * 
     * @param e the {@link Expression to simplify}
     * @return the simplified expression.
     */
    public static Expression simplify(Expression e) {
        return switch (e) {
            case Variable v   -> v;
            case And      and -> simplify(and.left()).and(simplify(and.right()));
            case Or       or  -> simplify(or.left()).or(simplify(or.right()));
            case Not      not -> simplifyNot(not);
        };
    }

    public static Expression simplifyNot(Not not) {
        return switch (not.expression()) {
            case Variable v     -> not;
            case And      and   -> simplify(and.left().not()).or(simplify(and.right().not()));
            case Or       or    -> simplify(or.left().not()).and(simplify(or.right().not()));
            case Not      nonot -> simplify(nonot.expression());
        };
    }
}
