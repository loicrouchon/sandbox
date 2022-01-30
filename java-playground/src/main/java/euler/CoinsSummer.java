package euler;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * <p><a href="https://projecteuler.net/problem=31">Coins sum</a></p>
 * <p>
 * <p>
 * In the United Kingdom the currency is made up of pound (£) and pence (p). There are eight coins in general circulation:
 * <p>
 * 1p, 2p, 5p, 10p, 20p, 50p, £1 (100p), and £2 (200p).
 * <p>
 * It is possible to make £2 in the following way:
 * <p>
 * 1×£1 + 1×50p + 2×20p + 1×5p + 1×2p + 3×1p
 * <p>
 * How many different ways can £2 be made using any number of coins?</p>
 */
public class CoinsSummer {

    private final int[] coinsValue;

    public CoinsSummer(int[] coinsValue) {

        this.coinsValue = coinsValue;
    }

    public int countCombinations(int amount) {
        return countCombinations(amount, 0, 0);
    }

    private int countCombinations(int amount, int currentSum, int pos) {
        int combinations = 0;
        for (int i = pos; i < coinsValue.length; i++) {
            int sum = currentSum + coinsValue[i];
            if (sum == amount) {
                combinations++;
            } else if (sum < amount) {
                combinations += countCombinations(amount, sum, i);
            }
        }
        return combinations;
    }

    public List<int[]> findCombinations(int amount) {
        List<int[]> combinations = new ArrayList<>();
        int[] base = new int[coinsValue.length];
        Arrays.fill(base, 0);
        findCombinations(combinations, amount, base, 0, 0);
        return combinations;
    }

    private void findCombinations(List<int[]> combinations, int amount, int[] base, int currentSum, int pos) {
        for (int i = pos; i < coinsValue.length; i++) {
            int sum = currentSum + coinsValue[i];
            if (sum == amount) {
                int[] combination = Arrays.copyOf(base, base.length);
                combination[i]++;
                combinations.add(combination);
            } else if (sum < amount) {
                int[] combination = Arrays.copyOf(base, base.length);
                combination[i]++;
                findCombinations(combinations, amount, combination, sum, i);
            }
        }
    }
}
