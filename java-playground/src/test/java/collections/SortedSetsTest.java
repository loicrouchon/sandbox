package collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.NavigableSet;
import java.util.Set;
import java.util.SortedSet;
import java.util.TreeSet;
import org.junit.jupiter.api.Test;

class SortedSetsTest {

    @Test
    void sortedSet() {
        //given
        var set = new TreeSet<Integer>();
        //when/then
        set.addAll(Set.of(16, 17, 18));
        set.addAll(Set.of(1, 2, 3));
//        assertThat(set).containsExactly(1, 2, 3);
        assertThat(set).containsExactly(1, 2, 3, 16, 17, 18);
    }

    @Test
    void subsets() {
        //given
        SortedSet<Integer> set = new TreeSet<>(Set.of(1, 2, 3, 4, 5, 6));
        //when/then
        assertThat(set.subSet(2, 5)).containsExactly(2, 3, 4);
        assertThat(set.headSet(4)).containsExactly(1, 2, 3);
        assertThat(set.tailSet(3)).containsExactly(3, 4, 5, 6);
    }

    @Test
    void closest() {
        //given
        NavigableSet<Integer> set = new TreeSet<>(Set.of(10, 20, 30));
        //when/then
        assertThat(set.floor(19)).isEqualTo(10);
        assertThat(set.floor(20)).isEqualTo(20);
        assertThat(set.floor(21)).isEqualTo(20);

        assertThat(set.lower(19)).isEqualTo(10);
        assertThat(set.lower(20)).isEqualTo(10);
        assertThat(set.lower(21)).isEqualTo(20);

        assertThat(set.ceiling(19)).isEqualTo(20);
        assertThat(set.ceiling(20)).isEqualTo(20);
        assertThat(set.ceiling(21)).isEqualTo(30);

        assertThat(set.higher(19)).isEqualTo(20);
        assertThat(set.higher(20)).isEqualTo(30);
        assertThat(set.higher(21)).isEqualTo(30);
    }
}
