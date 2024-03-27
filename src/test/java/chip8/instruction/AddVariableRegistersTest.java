package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class AddVariableRegistersTest
{
    @Test
    void givenVariableRegisterSet_whenAddVariableRegistersWithoutOverflow_thenFirstVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 30;
        int value2 = 12;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new AddVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(42);
    }

    @Test
    void givenVariableRegisterSet_whenAddVariableRegistersWithOverflow_thenFirstVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 255;
        int value2 = 12;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new AddVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(11);
    }

    @Test
    void givenVariableRegisterSet_whenAddVariableRegisters_thenSecondVariableRegisterIsUnchanged()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 30;
        int value2 = 12;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new AddVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x2).get()).isEqualTo(value2);
    }

    @Test
    void givenVariableRegisterSet_whenAddVariableRegistersWithoutOverflow_thenFlagRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 30;
        int value2 = 12;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);
        variableRegisters.get(0xF).set(1);

        new AddVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegisterSet_whenAddVariableRegistersWithOverflow_thenFlagRegisterIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 255;
        int value2 = 12;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);
        variableRegisters.get(0xF).set(0);

        new AddVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegisterSet_whenAddFlagRegisterToVariableRegisterWithoutOverflow_thenFlagRegisterIsZero_andVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 30;
        int value2 = 1;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0xF).set(value2);

        new AddVariableRegisters(variableRegisters, 0x1, 0xF).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(31);
        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegisterSet_whenAddVariableRegisterToFlagRegisterWithoutOverflow_thenFlagRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 1;
        int value2 = 30;
        variableRegisters.get(0xF).set(value1);
        variableRegisters.get(0x2).set(value2);

        new AddVariableRegisters(variableRegisters, 0xF, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }
}