package problem;

public class TrainingProblem2 {
    public int minN(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("cannot find minimum in an empty array");
        }
        int min = array[0];
        int previous = array[0];
        for (int i = 1; i < array.length; i++) {
            if (array[i] < previous) {
                min = array[i];
                break;
            }
        }
        return min;

    }

    public int minLogN(int[] array) {
        if (array.length == 0) {
            throw new IllegalArgumentException("cannot find minimum in an empty array");
        }
        int lowerBoundIndex = 0;
        int upperBoundIndex = array.length - 1;
        while (lowerBoundIndex != upperBoundIndex) {
            int middle = (lowerBoundIndex + upperBoundIndex) / 2;
            if (array[middle] < array[upperBoundIndex]) {
                upperBoundIndex = middle;
            } else {
                lowerBoundIndex = (middle == lowerBoundIndex) ? middle + 1 : middle;
            }
        }
        return array[lowerBoundIndex];

    }
}
