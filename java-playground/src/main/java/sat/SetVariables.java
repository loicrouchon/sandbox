package sat;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;
import java.util.stream.Stream;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode
public class SetVariables implements Variables {

    private static final Random RANDOM = new Random();

    private boolean conflicts;
    private final Set<Integer> knownToBeTrueVars;
    private final Set<Integer> knownToBeFalseVars;

    SetVariables() {
        this(false, new HashSet<>(), new HashSet<>());
    }

    SetVariables(boolean conflicts, Set<Integer> knownToBeTrueVars, Set<Integer> knownToBeFalseVars) {
        this.conflicts = conflicts;
        this.knownToBeTrueVars = new HashSet<>(knownToBeTrueVars);
        this.knownToBeFalseVars = new HashSet<>(knownToBeFalseVars);
    }

    @Override
    public int ordinal() {
        return knownToBeTrueVars.size() + (int) knownToBeFalseVars.stream()
                .filter(v -> !knownToBeTrueVars.contains(v))
                .count();
    }

    @Override
    public boolean hasConflicts() {
        return conflicts;
    }

    @Override
    public void set(int i, boolean value) {
        if (value) {
            knownToBeTrueVars.add(i);
        } else {
            knownToBeFalseVars.add(i);
        }
        if (!conflicts && knownToBeTrueVars.contains(i) && knownToBeFalseVars.contains(i)) {
            conflicts = true;
        }
    }

    @Override
    public boolean get(int i) {
        boolean isTrue = knownToBeTrueVars.contains(i);
        boolean isFalse = knownToBeFalseVars.contains(i);
        if (isTrue && !isFalse) {
            return true;
        }
        if (isFalse && !isTrue) {
            return false;
        }
        if (!isTrue && !isFalse) {
            set(i, RANDOM.nextBoolean());
            return get(i);
        }
        throw new UnsupportedOperationException(
                "Cannot not get the value of conflicting variable " + i);
    }

    @Override
    public SetVariables copy() {
        return new SetVariables(conflicts, knownToBeTrueVars, knownToBeFalseVars);
    }

    @Override
    public SetVariables merge(Variables other) {
        SetVariables o = (SetVariables) other;
        SetVariables merge = copy();
        merge.knownToBeTrueVars.addAll(o.knownToBeTrueVars);
        merge.knownToBeFalseVars.addAll(o.knownToBeFalseVars);
        for (Integer knownToBeTrueVar : merge.knownToBeTrueVars) {
            if (merge.knownToBeFalseVars.contains(knownToBeTrueVar)) {
                merge.conflicts = true;
                break;
            }
        }
        return merge;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        int max = Stream.concat(knownToBeTrueVars.stream(), knownToBeFalseVars.stream())
                .mapToInt(Integer::intValue)
                .max()
                .orElse(0);
        for (int i = 0; i <= max; i++) {
            sb.append(Variables.toChar(knownToBeTrueVars.contains(i), knownToBeFalseVars.contains(i)));
        }
        return String.format("{conflicts: %b, variables: [%s]}", conflicts, sb.toString());
    }

    public static SetVariables of(String variables) {
        char[] vars = variables.toCharArray();
        boolean conflicts = false;
        Set<Integer> knownToBeTrue = new HashSet<>();
        Set<Integer> knownToBeFalse = new HashSet<>();
        for (int i = 0; i < vars.length; i++) {
            switch (vars[i]) {
                case TRUE_CHAR:
                    knownToBeTrue.add(i);
                    break;
                case FALSE_CHAR:
                    knownToBeFalse.add(i);
                    break;
                case UNKNOWN_CHAR:
                    break;
                case CONFLICT_CHAR:
                    knownToBeTrue.add(i);
                    knownToBeFalse.add(i);
                    conflicts = true;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown variable state. Should be one of: T, F, U, C");
            }
        }
        return new SetVariables(conflicts, knownToBeTrue, knownToBeFalse);
    }

    @Override
    public boolean isSuperSetOf(Variables other) {
        SetVariables o = (SetVariables) other;
        return knownToBeTrueVars.containsAll(o.knownToBeTrueVars) &&
                knownToBeFalseVars.containsAll(o.knownToBeFalseVars);
    }
}
