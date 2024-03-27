package chip8.instruction;

import chip8.ProgramCounter;
import chip8.Stack;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;


class CallTest
{
    @Test
    void givenProgramCounter_andStack_whenCall_thenProgramCounterIsSetToLocation()
    {
        ProgramCounter programCounter = new ProgramCounter();
        programCounter.set(0x210);
        Stack stack = new Stack(programCounter);
        int location = 0x300;

        new Call(programCounter, stack, location).run();

        assertThat(programCounter.get()).isEqualTo(location);
    }

    @Test
    void givenProgramCounter_andStack_whenCall_thenProgramCounterPushed()
    {
        ProgramCounter programCounter = new ProgramCounter();
        programCounter.set(0x210);
        Stack stack = new Stack(programCounter);
        int location = 0x300;

        new Call(programCounter, stack, location).run();

        stack.popProgramCounter();
        assertThat(programCounter.get()).isEqualTo(0x210);
    }
}