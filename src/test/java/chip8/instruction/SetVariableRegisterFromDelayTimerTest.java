package chip8.instruction;

import chip8.NoopExecutorService;
import chip8.VariableRegisters;
import chip8.timer.DelayTimer;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.*;

import static org.assertj.core.api.Assertions.assertThat;

class SetVariableRegisterFromDelayTimerTest
{
    @Test
    void givenDelayTimerIsZero_whenSetVariableRegisterFromDelayTimer_thenVariableRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        DelayTimer delayTimer = new DelayTimer(new NoopExecutorService());

        new SetVariableRegisterFromDelayTimer(variableRegisters, delayTimer, 0x3).run();

        assertThat(variableRegisters.get(0x3).get()).isEqualTo(0);
    }

    @Test
    void givenDelayTimerIsSet_whenSetVariableRegisterFromDelayTimer_thenVariableRegisterIsCorrect()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        DelayTimer delayTimer = new DelayTimer(new NoopExecutorService());
        delayTimer.set(100);

        new SetVariableRegisterFromDelayTimer(variableRegisters, delayTimer, 0x3).run();

        assertThat(variableRegisters.get(0x3).get()).isEqualTo(100);
    }
}