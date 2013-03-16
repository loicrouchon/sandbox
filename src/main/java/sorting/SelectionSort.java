package sorting;

public class SelectionSort implements InPlaceSortAlgorithm {

    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    void sort(int[] array, int start, int end) {
        for (int i = start; i < end; i++) {
            int minIndex = i;
            for (int j = i + 1; j <= end; j++) {
                if (array[j] < array[minIndex]) {
                    minIndex = j;
                }
            }
            swap(array, i, minIndex);
        }
    }

    private void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
