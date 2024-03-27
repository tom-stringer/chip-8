package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class SubtractVariableRegistersTest
{
    @Test
    void givenVariableRegisterSet_whenSubtractVariableRegisters_thenFirstVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 8;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new SubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(42);
    }

    @Test
    void givenVariableRegisterSet_whenSubtractVariableRegisters_thenSecondVariableRegisterIsUnchanged()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 8;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new SubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x2).get()).isEqualTo(value2);
    }

    @Test
    void givenVariableRegisterSet_whenSubtractVariableRegistersWithoutBorrow_thenFlagRegisterIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 8;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);
        variableRegisters.get(0xF).set(0);

        new SubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegisterSet_whenSubtractVariableRegistersWithBorrow_thenFlagRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 255;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);
        variableRegisters.get(0xF).set(1);

        new SubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegisterSet_whenSubtractFlagRegisterFromVariableRegisterWithoutBorrow_thenFlagRegisterIsOne_andVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 1;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0xF).set(value2);

        new SubtractVariableRegisters(variableRegisters, 0x1, 0xF).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(49);
        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegisterSet_whenSubtractVariableRegisterFromFlagRegisterWithBorrow_thenFlagRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 1;
        int value2 = 1;
        variableRegisters.get(0xF).set(value1);
        variableRegisters.get(0x2).set(value2);

        new SubtractVariableRegisters(variableRegisters, 0xF, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }
}