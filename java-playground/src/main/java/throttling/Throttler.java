package throttling;

import java.time.Duration;
import java.time.Instant;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class Throttler implements AutoCloseable {

    private static final Random RANDOM = new Random();

    private final Clock clock;
    private final Semaphore semaphore;

    private final int maxTemporization;
    private final long tickInterval;
    private final int consecutivePeriodsBeforeRateIncrease;
    private final float increaseRate;
    private final float decayRate;

    private volatile int rate;
    private volatile long nextSchedule;

    public Throttler(Duration tickInterval, int initialRate, int maxTemporization,
                     int consecutivePeriodsBeforeRateIncrease, float increaseRate, float decayRate) {
        this.tickInterval = tickInterval.toMillis();
        clock = new Clock(tickInterval, this::newPeriod);
        this.rate = initialRate;
        this.maxTemporization = maxTemporization;
        this.consecutivePeriodsBeforeRateIncrease = consecutivePeriodsBeforeRateIncrease;
        this.increaseRate = increaseRate;
        this.decayRate = decayRate;
        semaphore = new Semaphore(0);
    }

    public void start() {
        nextSchedule = now();
        clock.start();
    }

    private synchronized void newPeriod() {
        if (now() > nextSchedule) {
            increaseRate();
            resetPermits();
        }
    }

    private void resetPermits() {
        semaphore.drainPermits();
        semaphore.release(rate);
    }

    private void increaseRate() {
        if (consecutivePeriodsBeforeRateIncrease > 0) {
            long durationSinceLastRateExhaustion = now() - nextSchedule;
            if (durationSinceLastRateExhaustion > consecutivePeriodsBeforeRateIncrease * tickInterval) {
                rate = Math.max(rate + 1, (int) (increaseRate * rate));
            }
        }
    }

    public void acquire() throws InterruptedException {
        semaphore.acquire();
        temporize();
    }

    private void temporize() throws InterruptedException {
        if (maxTemporization > 0) {
            Thread.sleep(RANDOM.nextInt(maxTemporization));
        }
    }

    public synchronized void pauseUntil(Instant instant) {
        long newSchedule = instant.toEpochMilli();
        if (newSchedule > now() && newSchedule > nextSchedule) {
            semaphore.drainPermits();
            nextSchedule = newSchedule;
            if (decayRate > 0) {
                rate = Math.max(1, Math.min(rate - 1, (int) (decayRate * rate)));
            }
        }
    }

    public void stop() {
        clock.stop();
    }

    @Override
    public void close() {
        stop();
    }

    private static long now() {
        return System.currentTimeMillis();
    }
}
