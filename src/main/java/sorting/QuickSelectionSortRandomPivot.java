package sorting;

public class QuickSelectionSortRandomPivot implements InPlaceSortAlgorithm {

    private final int threshold;

    public QuickSelectionSortRandomPivot(int threshold) {
        this.threshold = threshold;
    }

    public void sort(int[] array) {
        quickSort(array, 0, array.length - 1);
    }

    private void quickSort(int[] array, int p, int r) {
        if (p < r) {
            int q = partition(array, p, r);
            sort(array, p, q - 1);
            sort(array, q + 1, r);
        }
    }

    private void sort(int[] array, int start, int end) {
        int length = end - start;
        if (length > threshold) {
            quickSort(array, start, end);
        } else {
            new SelectionSort().sort(array, start, end);
        }
    }

    private int partition(int[] array, int p, int r) {
        if (r - p > 10) {
            int pivot = p + (int) (Math.random() * (r - p));
            swap(array, pivot, r);
        }
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

    @Override
    public String toString() {
        return QuickSelectionSortRandomPivot.class.getName() + "(threshold=" + threshold + ")";
    }
}