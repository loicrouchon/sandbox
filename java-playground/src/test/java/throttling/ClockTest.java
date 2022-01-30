package throttling;

import java.time.Duration;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class ClockTest {

    private AtomicInteger count = new AtomicInteger(0);

    @Test
    public void shouldThrowIfStartedTwice() {
        Clock clock = new Clock(Duration.ofMillis(100L), () -> count.incrementAndGet());
        clock.start();
        assertThatThrownBy(clock::start).isInstanceOf(IllegalThreadStateException.class);
        clock.stop();
    }

    @Test
    public void shouldThrowIfStartedAfterHavingBeenStopped() {
        Clock clock = new Clock(Duration.ofMillis(100L), () -> count.incrementAndGet());
        clock.start();
        clock.stop();
        assertThatThrownBy(clock::start).isInstanceOf(IllegalThreadStateException.class);
    }

    @Test
    public void shouldTickEveryInterval() throws InterruptedException {
        try (Clock clock = new Clock(Duration.ofMillis(100L), () -> count.incrementAndGet())) {
            clock.start();
            Thread.sleep(550L);
            clock.stop();
            assertThat(count.get()).isEqualTo(6);
        }
    }

    @Test
    public void shouldNotTickAfterBeingStopped() throws InterruptedException {
        try (Clock clock = new Clock(Duration.ofMillis(100L), () -> count.incrementAndGet())) {
            clock.start();
            Thread.sleep(150L);
            clock.stop();
            assertThat(count.get()).isEqualTo(2);
            Thread.sleep(550L);
            assertThat(count.get()).isEqualTo(2);
        }
    }
}
