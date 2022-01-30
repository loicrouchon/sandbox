package collections;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.*;
import org.junit.jupiter.api.Test;

class QueuesTest {

    @Test
    void fifo() {
        //given
        for (Queue<Integer> container : List.of(new ArrayDeque<Integer>(), new LinkedList<Integer>())) {
            //when/then
            container.add(1);
            container.add(3);
            container.add(2);
            assertThat(container.poll()).isEqualTo(1);
            assertThat(container.poll()).isEqualTo(3);
            assertThat(container.poll()).isEqualTo(2);
        }
    }

    @Test
    void lifo() {
        for (Deque<Integer> container : List.of(new ArrayDeque<Integer>(), new LinkedList<Integer>())) {
            //given/when/then
            container.push(1);
            container.push(3);
            container.push(2);
            assertThat(container.pop()).isEqualTo(2);
            assertThat(container.pop()).isEqualTo(3);
            assertThat(container.pop()).isEqualTo(1);
        }
    }

    @Test
    void minHeap() {
        //given
        Queue<Integer> container = new PriorityQueue<>();
        //when/then
        container.add(1);
        container.add(3);
        container.add(2);
        assertThat(container.poll()).isEqualTo(1);
        assertThat(container.poll()).isEqualTo(2);
        assertThat(container.poll()).isEqualTo(3);
    }

    @Test
    void maxHeap() {
        //given
        Queue<Integer> container = new PriorityQueue<>(Comparator.<Integer>naturalOrder().reversed());
        //when/then
        container.add(1);
        container.add(3);
        container.add(2);
        assertThat(container.poll()).isEqualTo(3);
        assertThat(container.poll()).isEqualTo(2);
        assertThat(container.poll()).isEqualTo(1);
    }
}
