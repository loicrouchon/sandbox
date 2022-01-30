package sat;

public interface Variables {

    char TRUE_CHAR = 'T';
    char FALSE_CHAR = 'F';
    char UNKNOWN_CHAR = 'U';
    char CONFLICT_CHAR = 'C';

    int ordinal();

    boolean hasConflicts();

    void set(int i, boolean value);

    boolean get(int i);

    Variables copy();

    Variables merge(Variables other);

    static Variables init() {
        return new SetVariables();
        //        return new ArrayVariables();
    }

    static Variables of(String variables) {
        return SetVariables.of(variables);
        //        return ArrayVariables.of(variables);
    }

    boolean isSuperSetOf(Variables variable);

    static char toChar(boolean isTrue, boolean isFalse) {
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
}
