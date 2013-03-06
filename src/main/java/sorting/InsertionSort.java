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
            while (insertionIndex > 0 && currentValue < result[insertionIndex - 1]) {
                result[insertionIndex] = result[insertionIndex - 1];
                insertionIndex--;
            }
            result[insertionIndex] = currentValue;
        }
        return result;
    }
}