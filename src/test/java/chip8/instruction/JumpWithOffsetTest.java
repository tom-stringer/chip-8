package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JumpWithOffsetTest
{
    @Test
    void givenVariableRegisterIsZero_whenJumpWithOffset_thenProgramCounterSetToLocation()
    {
        ProgramCounter programCounter = new ProgramCounter();
        VariableRegisters variableRegisters = new VariableRegisters();

        new JumpWithOffset(programCounter, variableRegisters, 0x234).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x234);
    }

    @Test
    void givenVariableRegisterIsSet_whenJumpWithOffset_thenProgramCounterSetToLocationPlusOffset()
    {
        ProgramCounter programCounter = new ProgramCounter();
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0).set(6);

        new JumpWithOffset(programCounter, variableRegisters, 0x234).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x23A);
    }
}