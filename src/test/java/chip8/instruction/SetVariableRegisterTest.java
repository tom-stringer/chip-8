package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SetVariableRegisterTest
{
    @Test
    void givenVariableRegisterIsZero_whenSetVariableRegister_thenVariableRegisterIsSet()
    {
        VariableRegisters variableRegisters = new VariableRegisters();

        new SetVariableRegister(variableRegisters, 0x3, 0x30).run();

        assertThat(variableRegisters.get(0x3).get()).isEqualTo(0x30);
    }
}