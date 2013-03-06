package sorting;

public class InsertionSort implements SortAlgorithm {

    public int[] sort(int[] array) {
        int n = array.length;
        int[] result = new int[n];
        if (n == 0) {
            return result;
        }
        result[0] = array[0];
        for (int i = 1; i < n; i++) {
            int currentValue = array[i];
            int insertionIndex = i;
            for (; insertionIndex > 0; insertionIndex--) {
                if (insertionIndex == 0 || currentValue > result[insertionIndex - 1]) {
                    break;
                }
            }
            if (insertionIndex < i) {
                System.arraycopy(result, insertionIndex, result, insertionIndex + 1, i - insertionIndex);
            }
            result[insertionIndex] = currentValue;
        }
        return result;
    }
}