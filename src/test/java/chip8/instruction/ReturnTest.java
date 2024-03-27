package chip8.instruction;

import chip8.ProgramCounter;
import chip8.Stack;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ReturnTest
{
    @Test
    void givenProgramCounter_andStack_whenReturn_thenProgramCounterIsSetToValueAtTopOfStack()
    {
        ProgramCounter programCounter = new ProgramCounter();
        programCounter.set(0x210);
        Stack stack = new Stack(programCounter);
        stack.pushProgramCounter();
        programCounter.set(0x300);

        new Return(stack).run();

        assertThat(programCounter.get()).isEqualTo(0x210);
    }
}