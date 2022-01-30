package idea;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Comparator;
import java.util.List;
import java.util.OptionalInt;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import org.junit.jupiter.api.Test;

public class StreamTest {

    @Test
    public void iCanOpenTheCurrentStreamChainWindowButOnlyOnce() {
        //given
        IntStream intStream = IntStream.rangeClosed(1, 20);
        //when
        int result = intStream.reduce(0, Integer::sum);
        //then
        assertThat(result).isEqualTo(210);
    }

    @Test
    public void evenWhenThereIsNoLocalVariableHoldingTheStream() {
        //given
        //when
        int result = IntStream.rangeClosed(1, 20).reduce(0, Integer::sum);
        //then
        assertThat(result).isEqualTo(210);
    }

    @Test
    public void iCanSeeWhichElementsAreFiltered() {
        //given
        IntStream intStream = IntStream.rangeClosed(1, 20);
        //when
        int result = intStream
                .filter(i -> i % 2 == 0)
                .reduce(0, Integer::sum);
        //then
        assertThat(result).isEqualTo(110);
    }

    @Test
    public void iCanSeeHowElementsAreMapped() {
        //given
        IntStream intStream = IntStream.rangeClosed(1, 20);
        //when
        int result = intStream
                .filter(i -> i % 2 == 0)
                .map(i -> 2 * i)
                .reduce(0, Integer::sum);
        //then
        assertThat(result).isEqualTo(220);
    }

    @Test
    public void iCanSeeHowElementsAreFlatMapped() {
        //given
        IntStream intStream = IntStream.rangeClosed(1, 20);
        //when
        int result = intStream
                .filter(i -> i % 2 == 0 && i < 5)
                .map(i -> 2 * i)
                .flatMap(i -> IntStream.rangeClosed(1, i))
                .reduce(0, Integer::sum);
        //then
        assertThat(result).isEqualTo(78);
    }

    @Test
    public void iCanSeeHowFirstElementsIsFound() {
        //given
        IntStream intStream = IntStream.rangeClosed(1, 10);
        //when
        OptionalInt result = intStream
                .filter(i -> i > 5)
                .findFirst();
        //then
        assertThat(result)
                .isPresent()
                .hasValue(6);
    }

    @Test
    public void iCanSeeHowElementsAreSorted() {
        //given
        IntStream intStream = IntStream.rangeClosed(1, 5);
        //when
        List<Integer> result = intStream
                .boxed()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        //then
        assertThat(result).containsExactly(5, 4, 3, 2, 1);
    }
}
