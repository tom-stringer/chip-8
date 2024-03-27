package chip8.timer;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicInteger;

import static java.lang.Thread.currentThread;

public class DelayTimer {

    private static final long NANOS_PER_CYCLE = 1_000_000_000 / 60;

    private final ExecutorService executorService;

    private final AtomicInteger value;

    private Future<?> countdownFuture;

    public DelayTimer(ExecutorService executorService) {
        this.executorService = executorService;
        this.value = new AtomicInteger();
    }

    public void set(int value) {
        if (countdownFuture != null)
        {
            countdownFuture.cancel(true);
        }

        this.value.set(value);

        countdownFuture = executorService.submit(this::startCountdown);
    }

    public int get() {
        return value.get();
    }

    private void startCountdown() {
        Thread currentThread = currentThread();

        while (!currentThread.isInterrupted() && value.get() > 0)
        {
            long resumeTime = System.nanoTime() + NANOS_PER_CYCLE;

            while (!currentThread.isInterrupted() && System.nanoTime() < resumeTime)
            {
                Thread.onSpinWait();
            }

            value.decrementAndGet();
        }
    }
}
