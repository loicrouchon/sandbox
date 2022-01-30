package euler;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import org.junit.jupiter.api.Test;

public class CoinsSummerTest {

    private static final int[] UK_COINS = {1, 2, 5, 10, 20, 50, 100, 200};

    @Test
    public void shouldFindCombinationsFor2Cents() {
        // given
        CoinsSummer coinsSummer = new CoinsSummer(UK_COINS);
        // when
        List<int[]> combinations = coinsSummer.findCombinations(2);
        // then
        assertThat(combinations).hasSize(2);
        assertThat(combinations).contains(new int[]{2, 0, 0, 0, 0, 0, 0, 0});
        assertThat(combinations).contains(new int[]{0, 1, 0, 0, 0, 0, 0, 0});
    }

    @Test
    public void shouldFindCombinationsFor5Cents() {
        // given
        CoinsSummer coinsSummer = new CoinsSummer(UK_COINS);
        // when
        List<int[]> combinations = coinsSummer.findCombinations(5);
        // then
        assertThat(combinations).hasSize(4);
        assertThat(combinations).contains(new int[]{5, 0, 0, 0, 0, 0, 0, 0});
        assertThat(combinations).contains(new int[]{3, 1, 0, 0, 0, 0, 0, 0});
        assertThat(combinations).contains(new int[]{1, 2, 0, 0, 0, 0, 0, 0});
        assertThat(combinations).contains(new int[]{0, 0, 1, 0, 0, 0, 0, 0});
    }

    @Test
    public void shouldFindCombinationsFor2Pounds() {
        // given
        CoinsSummer coinsSummer = new CoinsSummer(UK_COINS);
        // when
        List<int[]> combinations = coinsSummer.findCombinations(200);
        // then
        assertThat(combinations).hasSize(73682);
    }

    @Test
    public void shouldCountCombinationsFor2Cents() {
        // given
        CoinsSummer coinsSummer = new CoinsSummer(UK_COINS);
        // when
        int combinations = coinsSummer.countCombinations(2);
        // then
        assertThat(combinations).isEqualTo(2);
    }

    @Test
    public void shouldCountCombinationsFor5Cents() {
        // given
        CoinsSummer coinsSummer = new CoinsSummer(UK_COINS);
        // when
        int combinations = coinsSummer.countCombinations(5);
        // then
        assertThat(combinations).isEqualTo(4);
    }

    @Test
    public void shouldCountCombinationsFor2Pounds() {
        // given
        CoinsSummer coinsSummer = new CoinsSummer(UK_COINS);
        // when
        int combinations = coinsSummer.countCombinations(200);
        // then
        assertThat(combinations).isEqualTo(73682);
    }

}