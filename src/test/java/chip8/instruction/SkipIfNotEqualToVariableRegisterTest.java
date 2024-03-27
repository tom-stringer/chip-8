package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SkipIfNotEqualToVariableRegisterTest {

    @Test
    void givenVariableRegister_andDifferentValue_whenSkipIfNotEqualToVariableRegister_thenProgramCounterIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(2).set(42);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfNotEqualToVariableRegister(variableRegisters, programCounter, 2, 56).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x202);
    }

    @Test
    void givenVariableRegister_andSameValue_whenSkipIfNotEqualToVariableRegister_thenProgramCounterNotIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(2).set(42);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfNotEqualToVariableRegister(variableRegisters, programCounter, 2, 42).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x200);
    }
}