package chip8;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.assertThat;

class VariableRegistersTest
{
    @ParameterizedTest
    @ValueSource(ints = { 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF })
    void givenVariableRegisters_whenGet_thenVariableRegisterReturned(int x)
    {
        VariableRegisters variableRegisters = new VariableRegisters();

        VariableRegister variableRegister = variableRegisters.get(x);

        assertThat(variableRegister).isNotNull();
    }

    @Test
    void givenVariableRegisters_whenGet_thenAllUnique()
    {
        VariableRegisters variableRegisters = new VariableRegisters();

        Set<VariableRegister> registers = IntStream.rangeClosed(0x0, 0xF).mapToObj(variableRegisters::get).collect(Collectors.toSet());

        assertThat(registers).hasSize(16);
    }
}
