package collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.junit.jupiter.api.Test;

class IterablesTest {

    @Test
    void forLoop() {
        //given
        Iterable<Integer> ints = List.of(1, 2, 3);
        //when
        List<Integer> acc = new ArrayList<>();
        for (Integer anInt : ints) {
            acc.add(anInt);
        }
        //then
        assertThat(acc).isEqualTo(List.of(1, 2, 3));
    }

    @Test
    void forEach() {
        //given
        Iterable<Integer> ints = List.of(1, 2, 3);
        //when
        List<Integer> acc = new ArrayList<>();
        ints.forEach(acc::add);
        //then
        assertThat(acc).isEqualTo(List.of(1, 2, 3));
    }

    @Test
    void iterator() {
        //given
        Iterable<Integer> ints = List.of(1, 2, 3);
        //when
        List<Integer> acc = new ArrayList<>();
        Iterator<Integer> it = ints.iterator();
        while (it.hasNext()) {
            acc.add(it.next());
        }
        //then
        assertThat(acc).isEqualTo(List.of(1, 2, 3));
    }
}
