package chip8.timer;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

class DelayTimerTest
{
    private ExecutorService executorService;

    private DelayTimer delayTimer;

    @BeforeEach
    void createTimer()
    {
        executorService = Executors.newSingleThreadExecutor();
        delayTimer = new DelayTimer(executorService);

    }

    @AfterEach
    void destroyTimer()
    {
        executorService.shutdownNow();
    }

    @ParameterizedTest
    @MethodSource("delayMillisAndExpectedTimerValues")
    void givenDelayTimerSetTo60_whenTimeHasPassed_thenTimerIsAsExpected(int delayMillis, int expectedTimerValue) throws InterruptedException
    {
        delayTimer.set(60);

        Thread.sleep(delayMillis);

        Assertions.assertThat(delayTimer.get()).isEqualTo(expectedTimerValue);
    }

    public static Stream<Arguments> delayMillisAndExpectedTimerValues() {
        return Stream.of(
                Arguments.arguments(10, 60),
                Arguments.arguments(20, 59),
                Arguments.arguments(40, 58),
                Arguments.arguments(60, 57),
                Arguments.arguments(105, 54)
        );
    }

    @Test
    void givenDelayTimerSetTo60_whenASecondHasPassed_thenTimerIsSetTo0() throws InterruptedException {
        delayTimer.set(60);

        Thread.sleep(1000);

        Assertions.assertThat(delayTimer.get()).isEqualTo(0);
    }

    @Test
    void givenDelayTimerSetTo0_when100MillisecondsHavePassed_thenTimerIsUnchanged() throws InterruptedException {
        delayTimer.set(0);

        Thread.sleep(100);

        Assertions.assertThat(delayTimer.get()).isEqualTo(0);
    }
}