package problem;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;

import org.junit.Test;

public class BinaryMaskResolverTest {

    @Test
    public void shouldResolveOnlyZeros() {
        assertThat(Arrays.asList("00000000"), equalTo(solve("00000000")));
    }

    @Test
    public void shouldResolveOnlyOnes() {
        assertThat(Arrays.asList("11111111"), equalTo(solve("11111111")));
    }

    @Test
    public void shouldResolveOnlyZerosAndOnes() {
        assertThat(Arrays.asList("00101110"), equalTo(solve("00101110")));
    }

    @Test
    public void shouldResolveSingleQuestionMark() {
        assertThat(Arrays.asList("0", "1"), equalTo(solve("?")));
    }

    @Test
    public void shouldResolveMultiplesQuestionMark() {
        assertThat(Arrays.asList("0000", "1000", "0100", "1100", "0010", "1010", "0110", "1110", "0001", "1001",
                "0101", "1101", "0011", "1011", "0111", "1111"), equalTo(solve("????")));
    }

    @Test
    public void shouldResolveMixedMark() {
        assertThat(Arrays.asList("010000011", "011000011", "010001011", "011001011", "010000111", "011000111",
                "010001111", "011001111"), equalTo(solve("01?00??11")));
    }

    private List<String> solve(String mask) {
        return new BinaryMaskResolver().resolve(mask);
    }
}
