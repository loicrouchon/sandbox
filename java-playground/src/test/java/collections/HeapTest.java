package collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import org.junit.jupiter.api.Test;

class HeapTest {

    private static final List<Integer> ints = List.of(
            19, 7, 1, 13, 12, 17, 9, 4, 15, 18, 2, 11, 3, 5, 0, 14, 16, 6, 10, 8
    );

    @Test
    void minHeap() {
        //given
        Heap<Integer> container = new Heap<>(Integer::compare);
        List<Integer> results = new ArrayList<>();
        //when
        ints.forEach(container::push);
        while (!container.isEmpty()) {
            results.add(container.pop());
        }
        //then
        assertThat(results).isEqualTo(ints.stream().sorted().toList());
    }

    @Test
    void maxHeap() {
        //given
        Heap<Integer> container = new Heap<>(Comparator.<Integer>reverseOrder());
        List<Integer> results = new ArrayList<>();
        //when
        ints.forEach(container::push);
        while (!container.isEmpty()) {
            results.add(container.pop());
        }
        //then
        assertThat(results).isEqualTo(ints.stream().sorted(Comparator.reverseOrder()).toList());
    }
}
