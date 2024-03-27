package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SkipIfVariableRegistersEqualTest {

    @Test
    void givenVariableRegistersWithDifferentValues_whenSkipIfVariableRegistersEqual_thenProgramCounterNotIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(2).set(42);
        variableRegisters.get(3).set(43);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfVariableRegistersEqual(variableRegisters, programCounter, 2, 3).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x200);
    }

    @Test
    void givenVariableRegistersWithSameValue_whenSkipIfEqualToVariableRegister_thenProgramCounterIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value = 42;
        variableRegisters.get(3).set(value);
        variableRegisters.get(4).set(value);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfVariableRegistersEqual(variableRegisters, programCounter, 3, 4).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x202);
    }
}