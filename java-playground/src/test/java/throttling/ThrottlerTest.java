package throttling;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.time.Duration;
import java.time.Instant;
import org.junit.jupiter.api.Test;

public class ThrottlerTest {

    private static final Duration ONE_HUNDRED_MILLIS = Duration.ofMillis(100L);

    @Test
    public void shouldThrowIfStartedTwice() {
        Throttler throttler = new Throttler(ONE_HUNDRED_MILLIS, 1, 0,
                0, 0f, 0f);
        throttler.start();
        assertThatThrownBy(throttler::start).isInstanceOf(IllegalThreadStateException.class);
        throttler.stop();
    }

    @Test
    public void shouldThrowIfStartedAfterHavingBeenStopped() {
        Throttler throttler = new Throttler(ONE_HUNDRED_MILLIS, 1, 0,
                0, 0f, 0f);
        throttler.start();
        throttler.stop();
        assertThatThrownBy(throttler::start).isInstanceOf(IllegalThreadStateException.class);
    }

    @Test
    public void shouldTickEveryInterval() throws InterruptedException {
        try (Throttler throttler = new Throttler(ONE_HUNDRED_MILLIS, 1, 0,
                0, 0f, 0f)) {
            throttler.start();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 5; i++) {
                throttler.acquire();
            }
            long duration = System.currentTimeMillis() - start;
            throttler.stop();
            assertThat(duration).isBetween(400L, 599L);
        }
    }

    @Test
    public void shouldTickMultipleTimesPerInterval() throws InterruptedException {
        try (Throttler throttler = new Throttler(ONE_HUNDRED_MILLIS, 100, 0,
                0, 0f, 0f)) {
            throttler.start();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 500; i++) {
                throttler.acquire();
            }
            long duration = System.currentTimeMillis() - start;
            throttler.stop();
            assertThat(duration).isBetween(400L, 599L);
        }
    }

    @Test
    public void shouldResumeAfterBeingPaused() throws InterruptedException {
        try (Throttler throttler = new Throttler(ONE_HUNDRED_MILLIS, 10, 0,
                0, 0f, 0f)) {
            throttler.start();
            long start = System.currentTimeMillis();
            for (int i = 0; i < 20; i++) {
                throttler.acquire();
            }
            throttler.pauseUntil(Instant.ofEpochMilli(start).plusMillis(700L));
            assertThat(System.currentTimeMillis() - start).isBetween(100L, 300L);
            throttler.acquire();
            assertThat(System.currentTimeMillis() - start).isBetween(700L, 800L);
            for (int i = 0; i < 30; i++) {
                throttler.acquire();
            }
            assertThat(System.currentTimeMillis() - start).isBetween(900L, 1100L);
            throttler.stop();
        }
    }
}
