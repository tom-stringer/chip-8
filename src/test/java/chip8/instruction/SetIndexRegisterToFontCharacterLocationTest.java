package chip8.instruction;

import chip8.IndexRegister;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class SetIndexRegisterToFontCharacterLocationTest
{
    @ParameterizedTest
    @MethodSource("fontCharacterAndMemoryLocation")
    void givenIndexRegisterIsZero_whenSetIndexRegisterToFontCharacterLocation_thenIndexRegisterIsSet(int character, int characterLocation)
    {
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0xB).set(character);

        new SetIndexRegisterToFontCharacterLocation(variableRegisters, indexRegister, 0xB).run();

        assertThat(indexRegister.get()).isEqualTo(characterLocation);
    }

    private static Stream<Arguments> fontCharacterAndMemoryLocation()
    {
        return Stream.of(
                Arguments.arguments(0x0, 80),
                Arguments.arguments(0x1, 85),
                Arguments.arguments(0x2, 90),
                Arguments.arguments(0x3, 95),
                Arguments.arguments(0x4, 100),
                Arguments.arguments(0x5, 105),
                Arguments.arguments(0x6, 110),
                Arguments.arguments(0x7, 115),
                Arguments.arguments(0x8, 120),
                Arguments.arguments(0x9, 125),
                Arguments.arguments(0xA, 130),
                Arguments.arguments(0xB, 135),
                Arguments.arguments(0xC, 140),
                Arguments.arguments(0xD, 145),
                Arguments.arguments(0xE, 150),
                Arguments.arguments(0xF, 155)
        );
    }
}