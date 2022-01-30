package perf;

import java.time.Duration;
import java.util.Random;
import java.util.function.Supplier;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

public class Arithmetic {

    @Test
    @Disabled
    public void testStuff() {
        int[] ints = new int[100_000_000];
        Random random = new Random();
        for (int i = 0; i < ints.length; i++) {
            ints[i] = random.nextInt(256);
        }

        for (int n = 0; n < 10; n++) {
            time("add", () -> {
                double sum = 0d;
                for (int i : ints) {
                    sum += i + 1;
                }
                return sum;
            });
            time("multiply", () -> {
                double sum = 0d;
                for (int i : ints) {
                    sum += i * 2;
                }
                return sum;
            });
            time("power", () -> {
                double sum = 0d;
                for (int i : ints) {
                    sum += Math.pow(i, 1.2);
                }
                return sum;
            });
            time("exponent", () -> {
                double sum = 0d;
                for (int i : ints) {
                    sum += Math.exp(i);
                }
                return sum;
            });
            time("tan", () -> {
                double sum = 0d;
                for (int i : ints) {
                    sum = Math.tan(i);
                }
                return sum;
            });
            time("polytan", () -> {
                double sum = 0d;
                for (int i : ints) {
                    int i2 = i * i;
                    int i3 = i2 * i;
                    int i4 = i3 * i;
                    sum =  (0.999999986d * i - 0.0958010197d * i3) / (1 - 0.429135022d * i2 + 0.00971659383d * i4);
                }
                return sum;
            });
        }
    }

    private void time(String name, Supplier<Double> runnable) {
        System.gc();
        long start = System.currentTimeMillis();
        Double value = runnable.get();
        long end = System.currentTimeMillis();
        System.out.printf("Duration for %s: %s - value %s%n", name, Duration.ofMillis(end - start), value);
    }
}