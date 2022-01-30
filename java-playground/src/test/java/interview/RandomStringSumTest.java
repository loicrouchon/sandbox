package interview;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import org.junit.jupiter.api.Test;

public class RandomStringSumTest {

    public String sum(String str) {
        List<String> numbers = Arrays.stream(str.split("[^\\d]+"))
                .filter(s -> s != null && !s.isEmpty())
                .collect(Collectors.toList());
        if (numbers.isEmpty()) {
            return "0";
        }
        int sum = numbers.stream().mapToInt(Integer::parseInt).sum();
        String operations = numbers.stream().collect(Collectors.joining("+", "(", ")"));
        return sum + " " + operations;
    }

    @Test
    public void shouldSumSingleDigits() {
        assertThat(sum("1a2b3c")).isEqualTo("6 (1+2+3)");
    }

    @Test
    public void shouldSumMultiDigits() {
        assertThat(sum("123ab!b45c")).isEqualTo("168 (123+45)");
    }

    @Test
    public void shouldSumWithoutDigits() {
        assertThat(sum("abcdef")).isEqualTo("0");
    }

    @Test
    public void shouldSumDigitsBySplittingDecimals() {
        assertThat(sum("0123.4")).isEqualTo("127 (0123+4)");
    }

    @Test
    public void shouldSumDigitsWithSpecialChars() {
        assertThat(sum("dFD$#23+++12@#T1234;/.,10")).isEqualTo("1279 (23+12+1234+10)");
    }
}
