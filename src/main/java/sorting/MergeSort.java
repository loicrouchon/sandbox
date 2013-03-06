package sorting;

public class MergeSort implements InPlaceSortAlgorithm {

    public void sort(int[] array) {
        sort(array, 0, array.length - 1);
    }

    public void sort(int[] array, int start, int end) {
        if (end - start >= 1) {
            int middle = (end + start) / 2;
            sort(array, start, middle);
            sort(array, middle + 1, end);
            merge(array, start, middle + 1, end);
        }
    }

    public void merge(int[] array, int start, int middle, int end) {
        int leftIndex = start;
        int rightIndex = middle;
        if (array[middle - 1] <= array[rightIndex]) {
            return;
        }
        while (leftIndex < middle && rightIndex <= end) {
            if (array[leftIndex] <= array[rightIndex]) {
                leftIndex++;
            } else {
                int temp = array[rightIndex];
                System.arraycopy(array, leftIndex, array, leftIndex + 1, rightIndex - leftIndex);
                array[leftIndex] = temp;
                leftIndex++;
                middle++;
                rightIndex++;
            }
        }
    }
}