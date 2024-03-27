package chip8;

import chip8.ui.Display;
import chip8.display.DisplayModel;
import chip8.display.DisplayPresenter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.withinPercentage;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class EmulatorTest
{
    @Mock
    private FetchDecodeExecutor fetchDecodeExecutor;

    private Memory memory;

    private Emulator emulator;

    @BeforeEach
    void beforeEach()
    {
        memory = new Memory();
        emulator = new Emulator(new DisplayPresenter(new DisplayModel(), new Display()), memory, fetchDecodeExecutor, 100);
    }

    @Test
    void givenEmulator_whenLoadProgram_thenProgramIsLoadedIntoMemory()
    {
        byte[] program = new byte[] { 0x01, 0x02, 0x03, 0x04 };

        emulator.loadProgram(program);

        assertThat(memory.get(0x200)).isEqualTo((byte) 0x01);
        assertThat(memory.get(0x201)).isEqualTo((byte) 0x02);
        assertThat(memory.get(0x202)).isEqualTo((byte) 0x03);
        assertThat(memory.get(0x203)).isEqualTo((byte) 0x04);
    }

    @Test
    void givenEmulator_whenRun_thenFetchDecodeExecuteInvoked()
    {
        doAnswer(invocation -> {
            emulator.stop();
            return null;
        }).when(fetchDecodeExecutor).fetchDecodeExecute();

        emulator.run();

        verify(fetchDecodeExecutor).fetchDecodeExecute();
    }

    @Test
    void givenEmulator_whenRun_thenFetchDecodeExecuteInvokedAtApproximatelyCorrectRate()
    {
        AtomicInteger warmUpInstructions = new AtomicInteger(20);
        AtomicInteger instructions = new AtomicInteger(100);
        AtomicLong startTime = new AtomicLong();

        doAnswer(invocation -> {
            int warmUp = warmUpInstructions.decrementAndGet();

            if (warmUp == 0)
            {
                startTime.set(System.nanoTime());
            }

            if (warmUp <= 0)
            {
                if (instructions.decrementAndGet() == 0)
                {
                    emulator.stop();
                }
            }
            return null;

        }).when(fetchDecodeExecutor).fetchDecodeExecute();

        emulator.run();
        long endTime = System.nanoTime();

        assertThat(endTime - startTime.get()).isCloseTo(1_000_000_000L, withinPercentage(1.5));
    }
}