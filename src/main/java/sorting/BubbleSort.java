package sorting;

public class BubbleSort implements InPlaceSort {

    public void sort(int[] array) {
        int lastElementIndex = array.length - 1;
        if (lastElementIndex < 1) {
            return;
        }
        boolean sorted = false;
        while (!sorted) {
            sorted = true;
            for (int i = 0; i < lastElementIndex; i++) {
                if (array[i] > array[i + 1]) {
                    swap(array, i, i + 1);
                    sorted = false;
                }
            }
        }
    }

    public void swap(int[] array, int i, int j) {
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }
}
