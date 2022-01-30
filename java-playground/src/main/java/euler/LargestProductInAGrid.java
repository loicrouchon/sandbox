package euler;

import java.util.function.IntBinaryOperator;
import java.util.function.IntToLongFunction;
import java.util.function.IntUnaryOperator;

/**
 * <p><a href="https://projecteuler.net/problem=11">Largest product in a grid</a></p>
 * <p>
 * <p>
 * What is the greatest product of four adjacent numbers in the same direction (up, down, left, right, or diagonally) in the 20×20 grid?
 * <p>
 */
public class LargestProductInAGrid {

    private final int[][] matrix;

    public LargestProductInAGrid(int[][] matrix) {
        this.matrix = matrix;
    }

    public long largestProduct(int numbers) {
        long maxProduct = 1L;
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length - numbers; j++) {
                int fi = i;
                int fj = j;
                long currentProduct = product(n -> matrix[fi][fj + n], numbers);
                maxProduct = Math.max(currentProduct, maxProduct);
            }
        }
        for (int i = 0; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length - numbers; j++) {
                int fi = i;
                int fj = j;
                long currentProduct = product(n -> matrix[fj + n][fi], numbers);
                maxProduct = Math.max(currentProduct, maxProduct);
            }
        }
        for (int i = 0; i < matrix.length - numbers; i++) {
            for (int j = 0; j < matrix[i].length - numbers; j++) {
                int fi = i;
                int fj = j;
                long currentProduct = product(n -> matrix[fi + n][fj + n], numbers);
                maxProduct = Math.max(currentProduct, maxProduct);
            }
        }
        for (int i = numbers; i < matrix.length; i++) {
            for (int j = 0; j < matrix[i].length - numbers; j++) {
                int fi = i;
                int fj = j;
                long currentProduct = product(n -> matrix[fi - n][fj + n], numbers);
                maxProduct = Math.max(currentProduct, maxProduct);
            }
        }
        return maxProduct;
    }

    private static long product(IntUnaryOperator valueProvider, int numbers) {
        long product = 1L;
        for (int i = 0; i < numbers; i++) {
            product *= valueProvider.applyAsInt(i);
        }
        return product;
    }
}
