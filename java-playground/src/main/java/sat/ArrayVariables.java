package sat;

import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class ArrayVariables implements Variables {

    private static final Random RANDOM = new Random();

    private boolean conflicts = false;
    private boolean[] knownToBeTrueVars;
    private boolean[] knownToBeFalseVars;

    ArrayVariables() {
        knownToBeTrueVars = new boolean[0];
        knownToBeFalseVars = new boolean[0];
    }

    ArrayVariables(boolean conflicts, boolean[] knownToBeTrueVars, boolean[] knownToBeFalseVars) {
        this.conflicts = conflicts;
        this.knownToBeTrueVars = copyArray(knownToBeTrueVars, knownToBeTrueVars.length);
        this.knownToBeFalseVars = copyArray(knownToBeFalseVars, knownToBeFalseVars.length);
    }

    @Override
    public int ordinal() {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean hasConflicts() {
        return conflicts;
    }

    @Override
    public void set(int i, boolean value) {
        resize(i + 1);
        if (value) {
            knownToBeTrueVars[i] = true;
        } else {
            knownToBeFalseVars[i] = true;
        }
        if (!conflicts && knownToBeTrueVars[i] && knownToBeFalseVars[i]) {
            conflicts = true;
        }
    }

    @Override
    public boolean get(int i) {
        resize(i + 1);
        boolean isTrue = knownToBeTrueVars[i];
        boolean isFalse = knownToBeFalseVars[i];
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

    private void resize(int size) {
        if (size > knownToBeTrueVars.length) {
            knownToBeTrueVars = copyArray(knownToBeTrueVars, size);
            knownToBeFalseVars = copyArray(knownToBeFalseVars, size);
        }
    }

    @Override
    public ArrayVariables copy() {
        return new ArrayVariables(conflicts, knownToBeTrueVars, knownToBeFalseVars);
    }

    @Override
    public Variables merge(Variables other) {
        throw new UnsupportedOperationException();
    }

    @Override
    public int hashCode() {
        return Objects.hash(
                conflicts,
                Arrays.hashCode(knownVars(knownToBeTrueVars)),
                Arrays.hashCode(knownVars(knownToBeFalseVars)));
    }

    @Override
    public boolean equals(Object object) {
        if (object == this) {
            return true;
        }
        if (object == null || !ArrayVariables.class.equals(object.getClass())) {
            return false;
        }
        ArrayVariables other = (ArrayVariables) object;
        return Objects.equals(conflicts, other.conflicts) &&
                Arrays.equals(knownVars(knownToBeTrueVars), other.knownVars(knownToBeTrueVars)) &&
                Arrays.equals(knownVars(knownToBeFalseVars), other.knownVars(knownToBeFalseVars));
    }

    private boolean[] knownVars(boolean[] array) {
        int lastKnownIndex = array.length - 1;
        while (lastKnownIndex > 0 && !array[lastKnownIndex]) {
            lastKnownIndex--;
        }
        return Arrays.copyOfRange(array, 0, lastKnownIndex);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < knownToBeTrueVars.length; i++) {
            sb.append(toChar(knownToBeTrueVars[i], knownToBeFalseVars[i]));
        }
        return String.format("{conflicts: %b, variables: [%s]}", conflicts, sb.toString());
    }

    private static char toChar(boolean isTrue, boolean isFalse) {
        if (isTrue && !isFalse) {
            return TRUE_CHAR;
        }
        if (isFalse && !isTrue) {
            return FALSE_CHAR;
        }
        if (!isTrue && !isFalse) {
            return UNKNOWN_CHAR;
        }
        return CONFLICT_CHAR;
    }

    public static ArrayVariables of(String variables) {
        char[] vars = variables.toCharArray();
        boolean conflicts = false;
        boolean[] knownToBeTrue = new boolean[vars.length];
        boolean[] knownToBeFalse = new boolean[vars.length];
        for (int i = 0; i < vars.length; i++) {
            switch (vars[i]) {
                case TRUE_CHAR:
                    knownToBeTrue[i] = true;
                    break;
                case FALSE_CHAR:
                    knownToBeFalse[i] = true;
                    break;
                case UNKNOWN_CHAR:
                    break;
                case CONFLICT_CHAR:
                    knownToBeTrue[i] = true;
                    knownToBeFalse[i] = true;
                    conflicts = true;
                    break;
                default:
                    throw new IllegalArgumentException("Unknown variable state. Should be one of: T, F, U, C");
            }
        }
        return new ArrayVariables(conflicts, knownToBeTrue, knownToBeFalse);
    }

    @Override
    public boolean isSuperSetOf(Variables variable) {
        throw new UnsupportedOperationException();
    }

    private static boolean[] copyArray(boolean[] array, int size) {
        boolean[] copy = new boolean[size];
        System.arraycopy(array, 0, copy, 0, array.length);
        return copy;
    }
}
