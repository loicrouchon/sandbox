package sorting;

public class MergeSortWithArrayAllocations implements InPlaceSortAlgorithm {

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
        int[] sorted = new int[end - start + 1];
        int leftIndex = start;
        int rightIndex = middle;
        for (int i = 0; i < sorted.length; i++) {
            if (leftIndex < middle && (rightIndex > end || array[leftIndex] <= array[rightIndex])) {
                sorted[i] = array[leftIndex++];
            } else {
                sorted[i] = array[rightIndex++];
            }
        }
        System.arraycopy(sorted, 0, array, start, sorted.length);
    }
}