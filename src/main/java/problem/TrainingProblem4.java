package problem;

public class TrainingProblem4 {

    public void sortIntegersAndChars(Object[] array) {
        int middle = array.length / 2;
        int nextIntegerIndex = 0;
        int nextCharIndex = middle;
        for (int i = 0; i < array.length - 2; i += 2) {
            swap(array, i, nextIntegerIndex);
            swap(array, i + 1, nextCharIndex);
            nextIntegerIndex = Math.max(middle, i + 2);
            nextCharIndex++;
        }
    }

    private void swap(Object[] array, int i, int j) {
        Object tmp = array[i];
        array[i] = array[j];
        array[j] = tmp;
    }
}
