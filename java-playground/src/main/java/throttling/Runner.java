package throttling;

import java.time.Duration;
import java.time.Instant;
import java.util.Collection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.function.Supplier;

public class Runner {

    private static final Duration TICK_INTERVAL = Duration.ofSeconds(1L);
    private static final int MAX_TEMPORIZATION = 50;
    private static final int INITIAL_RATE = 10;
    private static final int CONSECUTIVE_PERIODS_BEFORE_RATE_INCREASE = 2;
    private static final float INCREASE_RATE = 1.1f;
    private static final float DECAY_RATE = 0.7f;

    private static final int WORKERS_POOL_SIZE = 10;
    private static final int RETRY_ATTEMPTS = 3;

    private static final class User {
    }

    public void synchronizeUsers(Collection<User> users) {
        try (Throttler throttler = new Throttler(TICK_INTERVAL, INITIAL_RATE, MAX_TEMPORIZATION,
                CONSECUTIVE_PERIODS_BEFORE_RATE_INCREASE, INCREASE_RATE, DECAY_RATE)) {
            throttler.start();

            ExecutorService executorService = Executors.newFixedThreadPool(WORKERS_POOL_SIZE);
            users.forEach(u -> executorService.submit(() -> retry(() -> synchronize(throttler, u), RETRY_ATTEMPTS)));
            executorService.shutdown();
            try {
                executorService.awaitTermination(1, TimeUnit.DAYS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void retry(Supplier<Boolean> action, int retryAttempts) {
        boolean success = false;
        while (!success && retryAttempts > 0) {
            success = action.get();
            retryAttempts--;
        }
    }

    public boolean synchronize(Throttler throttler, User user) {
        try {
            throttler.acquire();
        } catch (InterruptedException e) {
            return false;
        }
        // process user sync
        int statusCode = 200; // response of user sync
        if (statusCode == 200) {
            return true;
        }
        if (statusCode == 429) {
            Instant instant = computeResumeFromInstant();
            throttler.pauseUntil(instant);
        }
        return false;
    }

    // TODO implement this method
    private Instant computeResumeFromInstant() {
        Instant instant;
        boolean relativeWait = false; // TODO read the 429 body
        if (relativeWait) {
            long relativeWaitSeconds = 10L; // TODO read this value
            instant = Instant.now().plusSeconds(relativeWaitSeconds);
        } else {
            String instantAsString = ""; // TODO read this value
            instant = Instant.parse(instantAsString);
        }
        // TODO handle parsing exceptions with default
        return instant.plusSeconds(1L);
    }
}
