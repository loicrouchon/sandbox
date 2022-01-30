package idea;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;

public class FlameGraphTest {

    @Test
    public void computeSomethingExpensive() {
        //given/when
        int result = compute(6);
        //then
        assertThat(result).isEqualTo(720);
    }

    private int compute(int n) {
        if (n <= 0) {
            return 0;
        }
        if (n == 1) {
            return 1;
        }
        int result = 0;
        for (int i = 0; i < n; i++) {
            thisIsExpensive(n);
            result += compute(n - 1);
        }
        return result;
    }

    private static int thisIsExpensive(long millis) {
        int n = 0;
        for (int i = 0; i < 1000 * Math.pow(millis, millis); i++) {
            n = i;
        }
        return n;
    }
}
