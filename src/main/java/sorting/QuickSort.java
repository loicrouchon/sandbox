package sorting;

public class QuickSort implements InPlaceSort {

    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int p, int r) {
        if (p < r) {
            int q = partition(array, p, r);
            quickSort(array, p, q - 1);
            quickSort(array, q + 1, r);
        }
    }

    private int partition(int[] array, int p, int r) {
        boolean allElementsIdentical = true;
        int x = array[r];
        int i = p - 1;
        for (int j = p; j < r; j++) {
            if (array[j] < x) {
                allElementsIdentical = false;
                i++;
                swap(array, i, j);
            } else if (array[j] == x) {
                i++;
                swap(array, i, j);
            } else {
                allElementsIdentical = false;
            }
        }
        i++;
        swap(array, i, r);
        if (allElementsIdentical) {
            i = (p + r) / 2;
        }
        return i;
    }

    private void swap(int[] array, int a, int b) {
        int temp = array[a];
        array[a] = array[b];
        array[b] = temp;
    }
}