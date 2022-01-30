package collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.IntStream;
import lombok.AllArgsConstructor;
import lombok.ToString;
import org.junit.jupiter.api.Test;

class HashyMappyTest {

    @ToString
    @AllArgsConstructor
    static class Box {

        int value;

        @Override
        public int hashCode() {
            return value;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) {
                return true;
            }
            if (o == null || getClass() != o.getClass()) {
                return false;
            }
            Box box = (Box) o;
            return value == box.value;
        }
    }

    @Test
    void mutatedHash() {
        //given
        Box box = new Box(1);
        Map<Box, Integer> map = new HashMap<>(Map.of(box, 1));
        map.remove(box);
        assertThat(map).isEmpty();
        //and
        map.put(box, 1);
        //when
        box.value = 2;
        map.remove(box);
        //then
        // Should be 0 but the hash of the element has been modified
        assertThat(map).hasSize(1);
        // The object should have been removed, but it's still there
        // because of the mutated hash and can't be found because of this
        assertThat(map.containsKey(box)).isFalse();
        assertThat(map.get(box)).isNull();
    }

    @Test
    void hashyMappy() {
        //given
        List<Integer> ints = IntStream.range(0, 20).boxed().toList();
        var mappy = new HashyMappy<Box, Integer>();
        //when
        ints.forEach(i -> mappy.put(new Box(i), i));
        //then
        ints.forEach(i -> assertThat(mappy.get(new Box(i))).isEqualTo(i));
    }

    @Test
    void hashyMappy_mutableKey() {
        //given
        List<Integer> ints = IntStream.range(0, 20).boxed().toList();
        var mappy = new HashyMappy<Box, Integer>();
        //when
        ints.forEach(i -> mappy.put(new Box(i), i));
        Box key13 = new Box(13);
        assertThat(mappy.get(key13)).isEqualTo(13);
        mappy.put(key13, 35);
        assertThat(mappy.get(key13)).isEqualTo(35);
        //then
        key13.value = 35;
        assertThat(mappy.get(key13)).isNull();
        key13.value = 12;
        assertThat(mappy.get(key13)).isEqualTo(12);
    }
}
