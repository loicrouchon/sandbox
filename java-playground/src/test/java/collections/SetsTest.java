package collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.HashSet;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.junit.jupiter.api.Test;

class SetsTest {

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
        var set = new HashSet<>(Set.of(box));
        //when
        set.remove(box);
        //then
        assertThat(set).isEmpty();

        //given
        set = new HashSet<>(Set.of(box));
        //when
        box.value = 2;
        set.remove(box);
        //then
        // Should be 0 but the hash of the element has been modified
        assertThat(set).hasSize(1);
        // The object should have been removed, but it's still there
        // because of the mutated hash and can't be found because of this
        assertThat(set.contains(box)).isFalse();
    }

    @Test
    void union() {
        //given
        var a = Set.of(1, 2, 3);
        var b = Set.of(2, 3, 4);
        //when
        var union = new HashSet<>(a);
        union.addAll(b);
        //then
        assertThat(union).isEqualTo(Set.of(1, 2, 3, 4));
    }

    @Test
    void intersection() {
        //given
        var a = Set.of(1, 2, 3);
        var b = Set.of(2, 3, 4);
        //when
        var intersection = new HashSet<>(a);
        intersection.retainAll(b);
        //then
        assertThat(intersection).isEqualTo(Set.of(2, 3));
    }

    @Test
    void leftDiff() {
        //given
        var a = Set.of(1, 2, 3);
        var b = Set.of(2, 3, 4);
        //when
        var diff = new HashSet<>(a);
        diff.removeAll(b);
        //then
        assertThat(diff).isEqualTo(Set.of(1));
    }

    @Test
    void rightDiff() {
        //given
        var a = Set.of(1, 2, 3);
        var b = Set.of(2, 3, 4);
        //when
        var diff = new HashSet<>(b);
        diff.removeAll(a);
        //then
        assertThat(diff).isEqualTo(Set.of(4));
    }

    @Test
    void noOrder() {
        //given
        var set = new HashSet<>(10);
        //when/then
        set.addAll(Set.of(1, 2, 3));
        assertThat(set).containsExactly(1, 2, 3);
        set.addAll(Set.of(16, 17, 18));
        assertThat(set).containsExactly(16, 1, 17, 2, 18, 3);
    }
}
