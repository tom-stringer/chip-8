package chip8.instruction;

import chip8.NoopExecutorService;
import chip8.VariableRegisters;
import chip8.timer.DelayTimer;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SetDelayTimerFromVariableRegisterTest
{
    @Test
    void givenVariableRegisterIsSet_whenSetDelayTimerFromVariableRegister_thenDelayTimerIsCorrect()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        DelayTimer delayTimer = new DelayTimer(new NoopExecutorService());
        variableRegisters.get(0x4).set(100);

        new SetDelayTimerFromVariableRegister(variableRegisters, delayTimer, 0x4).run();

        assertThat(delayTimer.get()).isEqualTo(100);
    }
}