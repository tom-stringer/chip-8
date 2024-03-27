package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class NegativeSubtractVariableRegistersTest
{
    @Test
    void givenVariableRegistersSet_whenNegativeSubtractVariableRegisters_thenFirstVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 2;
        int value2 = 1;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(0xFF);
    }

    @Test
    void givenVariableRegistersSetWithSecondRegisterGreaterThanFirstRegister_whenNegativeSubtractVariableRegisters_thenVFIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 8;
        int value2 = 50;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegistersSetWithSecondRegisterLessThanFirstRegister_whenNegativeSubtractVariableRegisters_thenVFIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 8;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegistersSetWithSecondRegisterEqualToFirstRegister_whenNegativeSubtractVariableRegisters_thenVFIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 50;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegistersSet_whenNegativeSubtractVariableRegisters_thenSecondVariableRegisterIsUnchanged()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 50;
        int value2 = 8;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x2).get()).isEqualTo(value2);
    }

    @Test
    void givenVariableRegisterSet_whenNegativeSubtractVariableRegistersWithoutBorrow_thenFlagRegisterIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 8;
        int value2 = 50;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);
        variableRegisters.get(0xF).set(0);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegisterSet_whenNegativeSubtractVariableRegistersWithBorrow_thenFlagRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 255;
        int value2 = 50;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);
        variableRegisters.get(0xF).set(1);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegisterSet_whenNegativeSubtractFlagRegisterFromVariableRegisterWithoutBorrow_thenFlagRegisterIsOne_andVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 1;
        int value2 = 50;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0xF).set(value2);

        new NegativeSubtractVariableRegisters(variableRegisters, 0x1, 0xF).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo(49);
        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegisterSet_whenNegativeSubtractVariableRegisterFromFlagRegisterWithBorrow_thenFlagRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 1;
        int value2 = 1;
        variableRegisters.get(0xF).set(value1);
        variableRegisters.get(0x2).set(value2);

        new NegativeSubtractVariableRegisters(variableRegisters, 0xF, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }
}