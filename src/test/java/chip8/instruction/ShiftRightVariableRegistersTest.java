package chip8.instruction;

import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class ShiftRightVariableRegistersTest
{
    @ParameterizedTest
    @ValueSource(ints = {1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048})
    void givenVariableRegisterSet_whenShiftRightVariableRegisters_thenFirstVariableRegisterUpdatedToCorrectValue(int value1)
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftRightVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo((value1 & 0xFF) / 2);
    }

    @ParameterizedTest
    @ValueSource(ints = {2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048})
    void givenVariableRegisterSetToOddNumber_whenShiftRightVariableRegisters_thenFirstVariableRegisterUpdatedToCorrectValue(int value1)
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1 + 1);
        variableRegisters.get(0x2).set(value2);

        new ShiftRightVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x1).get()).isEqualTo((value1 & 0xFF) / 2);
    }

    @Test
    void givenVariableRegisterSetWithOddNumber_whenShiftRightVariableRegisters_thenFlagRegisterIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 0b0001;
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftRightVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenVariableRegisterSetWithEvenNumber_whenShiftRightVariableRegisters_thenFlagRegisterIsZero()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 0b0010;
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftRightVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenVariableRegisterSet_whenShiftRightVariableRegisters_thenSecondVariableRegisterIsUnchanged()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value1 = 0b0100;
        int value2 = 0b1111;
        variableRegisters.get(0x1).set(value1);
        variableRegisters.get(0x2).set(value2);

        new ShiftRightVariableRegisters(variableRegisters, 0x1, 0x2).run();

        assertThat(variableRegisters.get(0x2).get()).isEqualTo(value2);
    }

    @Test
    void givenFlagRegisterSetToOne_whenShiftRightFlagRegister_thenFlagRegisterIsOne()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        int value2 = 0b1111;
        variableRegisters.get(0xF).set(1);
        variableRegisters.get(0x2).set(value2);

        new ShiftRightVariableRegisters(variableRegisters, 0xF, 0x2).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }
}