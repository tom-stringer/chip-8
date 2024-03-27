package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.assertj.core.api.Assertions.assertThat;

class ShiftLeftVariableRegistersTest
{
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024})
    void givenVariableRegisterSet_whenShiftLeftVariableRegisters_thenFirstVariableRegisterUpdatedToCorrectValue(int value1)
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftLeftVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo((value1 * 2) & 0xFF);
    }

    @Test
    void givenVariableRegisterSetWithMostSignificantBitOne_whenShiftLeftVariableRegisters_thenFirstVariableRegisterUpdatedToCorrectValue()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 2048;
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftLeftVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isZero();
    }

    @Test
    void givenVariableRegisterSetWithMostSignificantBitOne_whenShiftLeftVariableRegisters_thenVFIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 0xFF;
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftLeftVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegisterSetWithMostSignificantBitZero_whenShiftLeftVariableRegisters_thenVFIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 1024;
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftLeftVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegisterSet_whenShiftLeftVariableRegisters_thenSecondVariableRegisterIsUnchanged()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 0b0100;
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftLeftVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x2).get()).isEqualTo(value2);
    }

    @Test
    void givenFlagRegisterMostSignificantBitSetToOne_whenShiftLeftFlagRegister_thenFlagRegisterIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0xF).set(0b1000_0000);
        variableRegisters.get(0x2).set(0b1111);

        new ShiftLeftVariableRegisters(variableRegisters, 0xF, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }
}