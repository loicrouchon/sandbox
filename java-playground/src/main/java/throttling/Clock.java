package throttling;

import java.time.Duration;

/**
 * A clock that will trigger a call to the tick consumer every tick interval.
 *
 * <strong>A clock must be stopped by calling the <code>stop()</code> method to avoid thread leakage.</strong>
 */
public class Clock implements AutoCloseable {

    private volatile boolean stop = false;

    private final long tickInterval;
    private final Runnable tickConsumer;
    private final Thread thread;

    /**
     * Instantiate a new clock without starting ticking.
     *
     * @param tickInterval the interval between two ticks. Note that this is the minimum duration between two ticks,
     *                     there is no guarantee that a new tick will be triggered after the exact tick interval.
     * @param tickConsumer the method to call on a new tick.
     */
    public Clock(Duration tickInterval, Runnable tickConsumer) {
        this.tickInterval = tickInterval.toMillis();
        this.tickConsumer = tickConsumer;
        thread = new Thread(this::tick, "clock-ticker-" + tickInterval + "-ms");
    }

    /**
     * Starts the clock.
     *
     * @throws IllegalThreadStateException if the clock has already been started
     */
    public synchronized void start() throws IllegalThreadStateException {
        thread.start();
    }

    private void tick() {
        while (!stop) {
            tickConsumer.run();
            try {
                Thread.sleep(tickInterval);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                // TODO log.debug("Clock thread has been interrupted. It will end", e);
                stop = true;
            }
        }
    }

    /**
     * Stops the clock
     */
    public synchronized void stop() {
        if (thread.isAlive()) {
            stop = true;
            thread.interrupt();
        }
    }

    @Override
    public void close() {
        stop();
    }
}
