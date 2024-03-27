package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AndVariableRegistersTest
{
    @Test
    void givenVariableRegistersSet_whenAndVariableRegisters_thenFirstVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 0b0110;
        int value2 = 0b1010;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new AndVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(0b0010);
    }

    @Test
    void givenVariableRegistersSet_whenAndVariableRegisters_thenSecondVariableRegisterIsUnchanged()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 0b1110;
        int value2 = 0b1010;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new AndVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x2).get()).isEqualTo(value2);
    }
}