package problem;

public class TrainingProblem3 {

    public int[][] maxSubMatrix(int[][] matrix) {
        int height = matrix.length;
        int width = height > 0 ? matrix[0].length : 0;
        int maxSubMatrixLeftIndex = 0;
        int maxSubMatrixRightIndex = 0;
        int maxSubMatrixLowerIndex = 0;
        int maxSubMatrixUpperIndex = 0;
        int maxSubMatrixSum = Integer.MIN_VALUE;
        for (int leftIndex = 0; leftIndex < width; leftIndex++) {
            for (int rightIndex = leftIndex; rightIndex < width; rightIndex++) {
                for (int lowerIndex = 0; lowerIndex < height; lowerIndex++) {
                    for (int upperIndex = lowerIndex; upperIndex < height; upperIndex++) {
                        int sum = computeSum(matrix, leftIndex, rightIndex, lowerIndex, upperIndex);
                        if (sum > maxSubMatrixSum) {
                            maxSubMatrixSum = sum;
                            maxSubMatrixLeftIndex = leftIndex;
                            maxSubMatrixRightIndex = rightIndex;
                            maxSubMatrixLowerIndex = lowerIndex;
                            maxSubMatrixUpperIndex = upperIndex;
                        }
                    }
                }
            }
        }
        int[][] maxSubMatrix = new int[maxSubMatrixUpperIndex - maxSubMatrixLowerIndex + 1][maxSubMatrixRightIndex
                - maxSubMatrixLeftIndex + 1];
        for (int x = maxSubMatrixLeftIndex; x <= maxSubMatrixRightIndex; x++) {
            for (int y = maxSubMatrixLowerIndex; y <= maxSubMatrixUpperIndex; y++) {
                maxSubMatrix[y - maxSubMatrixLowerIndex][x - maxSubMatrixLeftIndex] = matrix[y][x];
            }
        }
        return maxSubMatrix;
    }

    private int computeSum(int[][] matrix, int leftIndex, int rightIndex, int lowerIndex, int upperIndex) {
        int sum = 0;
        for (int x = leftIndex; x <= rightIndex; x++) {
            for (int y = lowerIndex; y <= upperIndex; y++) {
                sum += matrix[y][x];
            }
        }
        return sum;
    }
}
