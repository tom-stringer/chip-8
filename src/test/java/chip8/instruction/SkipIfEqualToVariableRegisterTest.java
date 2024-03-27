package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SkipIfEqualToVariableRegisterTest {

    @Test
    void givenVariableRegister_andDifferentValue_whenSkipIfEqualToVariableRegister_thenProgramCounterNotIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(2).set(42);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfEqualToVariableRegister(variableRegisters, programCounter, 2, 56).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x200);
    }

    @Test
    void givenVariableRegister_andSameValue_whenSkipIfEqualToVariableRegister_thenProgramCounterIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(2).set(42);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfEqualToVariableRegister(variableRegisters, programCounter, 2, 42).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x202);
    }
}