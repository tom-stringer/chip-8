package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SkipIfVariableRegistersNotEqualTest {

    @Test
    void givenVariableRegistersWithDifferentValues_whenSkipIfVariableRegistersNotEqual_thenProgramCounterIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(2).set(42);
        variableRegisters.get(3).set(43);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfVariableRegistersNotEqual(variableRegisters, programCounter, 2, 3).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x202);
    }

    @Test
    void givenVariableRegistersWithSameValue_whenSkipIfNotEqualToVariableRegister_thenProgramCounterNotIncremented()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value = 42;
        variableRegisters.get(3).set(value);
        variableRegisters.get(4).set(value);
        ProgramCounter programCounter = new ProgramCounter();

        new SkipIfVariableRegistersNotEqual(variableRegisters, programCounter, 3, 4).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x200);
    }
}