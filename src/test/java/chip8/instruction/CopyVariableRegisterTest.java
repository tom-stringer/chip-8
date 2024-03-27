package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class CopyVariableRegisterTest
{
    @Test
    void givenVariableRegisterSet_whenCopyToOtherVariableRegister_thenOtherVariableRegisterSet()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value = 22;
        variableRegisters.get(0).set(value);

        new CopyVariableRegister(variableRegisters, 1, 0).run();

        assertThat(variableRegisters.get(1).get()).isEqualTo(value);
    }

    @Test
    void givenVariableRegisterSet_whenCopyToOtherVariableRegister_thenOriginalVariableRegisterHasCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value = 22;
        variableRegisters.get(0).set(value);

        new CopyVariableRegister(variableRegisters, 1, 0).run();

        assertThat(variableRegisters.get(0).get()).isEqualTo(value);
    }
}