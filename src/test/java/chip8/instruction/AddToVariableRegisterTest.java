package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddToVariableRegisterTest
{
    @Test
    void givenVariableRegisterIsZero_whenAddVariableRegister_thenVariableRegisterIsUpdated()
    {
        VariableRegisters variableRegisters = new VariableRegisters();

        new AddToVariableRegister(variableRegisters, 0x3, 0x30).run();

        assertThat(variableRegisters.get(0x3).get()).isEqualTo(0x30);
    }

    @Test
    void givenVariableRegisterIsMaxValue_whenAddVariableRegister_thenVariableRegisterIsUpdated()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0x3).set(0xFF);

        new AddToVariableRegister(variableRegisters, 0x3, 0x30).run();

        assertThat(variableRegisters.get(0x3).get()).isEqualTo(0x2F);
    }
}