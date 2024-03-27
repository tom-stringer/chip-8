package chip8.instruction;

import chip8.*;
import chip8.decoder.InstructionDecoder;
import chip8.display.DisplayModel;
import chip8.input.Keyboard;
import chip8.timer.DelayTimer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class InstructionDecoderTest
{
    private InstructionDecoder instructionDecoder;

    @BeforeEach
    void setup()
    {
        ProgramCounter programCounter = new ProgramCounter();
        instructionDecoder = new InstructionDecoder(programCounter, new Stack(programCounter), new IndexRegister(), new VariableRegisters(), new DisplayModel(), new Memory(), new Keyboard(), new DelayTimer(Executors.newSingleThreadExecutor()), new Random());
    }

    @Test
    void given0x00E0Instruction_whenDecoded_thenClearDisplayReturned()
    {
        Runnable instruction = instructionDecoder.decode(0x00E0);

        assertThat(instruction).isInstanceOf(ClearDisplay.class);
    }

    @Test
    void given0x00EEInstruction_whenDecoded_thenReturnReturned()
    {
        Runnable instruction = instructionDecoder.decode(0x00EE);

        assertThat(instruction).isInstanceOf(Return.class);
    }

    @Test
    void given0x1NNNInstruction_whenDecoded_thenJumpWithCorrectLocationReturned()
    {
        Runnable instruction = instructionDecoder.decode(0x1300);

        assertThat(instruction).isInstanceOf(Jump.class).extracting("location").isEqualTo(0x300);
    }

    @Test
    void given0x2NNNInstruction_whenDecoded_thenCallWithCorrectLocationReturned()
    {
        Runnable instruction = instructionDecoder.decode(0x2400);

        assertThat(instruction).isInstanceOf(Call.class).extracting("location").isEqualTo(0x400);
    }

    @Test
    void given0x3XNNInstruction_whenDecoded_thenSkipIfEqualToVariableRegister()
    {
        Runnable instruction = instructionDecoder.decode(0x3242);

        assertThat(instruction).isInstanceOf(SkipIfEqualToVariableRegister.class);
        assertThat(instruction).extracting("x").isEqualTo(0x2);
        assertThat(instruction).extracting("n").isEqualTo(0x42);
    }

    @Test
    void given0x4XNNInstruction_whenDecoded_thenSkipIfNotEqualToVariableRegister()
    {
        Runnable instruction = instructionDecoder.decode(0x4353);

        assertThat(instruction).isInstanceOf(SkipIfNotEqualToVariableRegister.class);
        assertThat(instruction).extracting("x").isEqualTo(0x3);
        assertThat(instruction).extracting("n").isEqualTo(0x53);
    }

    @Test
    void given0x5XY0Instruction_whenDecoded_thenSkipIfVariableRegistersEqual()
    {
        Runnable instruction = instructionDecoder.decode(0x5840);

        assertThat(instruction).isInstanceOf(SkipIfVariableRegistersEqual.class);
        assertThat(instruction).extracting("x").isEqualTo(0x8);
        assertThat(instruction).extracting("y").isEqualTo(0x4);
    }

    @Test
    void given0x6XNNInstruction_whenDecoded_thenSetVariableRegisterWithCorrectRegisterAndValueReturned()
    {
        Runnable instruction = instructionDecoder.decode(0x6330);

        assertThat(instruction).isInstanceOf(SetVariableRegister.class);
        assertThat(instruction).extracting("x").isEqualTo(0x3);
        assertThat(instruction).extracting("k").isEqualTo(0x30);
    }

    @Test
    void given0x7XNNInstruction_whenDecoded_thenAddVariableRegisterWithCorrectRegisterAndValueReturned()
    {
        Runnable instruction = instructionDecoder.decode(0x7330);

        assertThat(instruction).isInstanceOf(AddToVariableRegister.class);
        assertThat(instruction).extracting("x").isEqualTo(0x3);
        assertThat(instruction).extracting("k").isEqualTo(0x30);
    }

    @ParameterizedTest
    @MethodSource("arithmeticInstructions")
    void given0x8XYNArithmeticInstruction_whenDecoded_thenCorrectInstructionAndVariableRegistersReturned(int instructionCode, Class<? extends Runnable> instructionClass, int x, int y)
    {
        Runnable instruction = instructionDecoder.decode(instructionCode);

        assertThat(instruction).isInstanceOf(instructionClass);
        assertThat(instruction).extracting("x").isEqualTo(x);
        assertThat(instruction).extracting("y").isEqualTo(y);
    }

    private static Stream<Arguments> arithmeticInstructions() {
        return Stream.of(
                arguments(0x8120, CopyVariableRegister.class, 0x1, 0x2),
                arguments(0x8231, OrVariableRegisters.class, 0x2, 0x3),
                arguments(0x8342, AndVariableRegisters.class, 0x3, 0x4),
                arguments(0x8453, XorVariableRegisters.class, 0x4, 0x5),
                arguments(0x8564, AddVariableRegisters.class, 0x5, 0x6),
                arguments(0x8675, SubtractVariableRegisters.class, 0x6, 0x7),
                arguments(0x8786, ShiftRightVariableRegisters.class, 0x7, 0x8),
                arguments(0x8897, NegativeSubtractVariableRegisters.class, 0x8, 0x9),
                arguments(0x89AE, ShiftLeftVariableRegisters.class, 0x9, 0xA)
        );
    }

    @Test
    void given0x9XY0Instruction_whenDecoded_thenSkipIfVariableRegistersNotEqual()
    {
        Runnable instruction = instructionDecoder.decode(0x9950);

        assertThat(instruction).isInstanceOf(SkipIfVariableRegistersNotEqual.class);
        assertThat(instruction).extracting("x").isEqualTo(0x9);
        assertThat(instruction).extracting("y").isEqualTo(0x5);
    }

    @Test
    void given0xANNNInstruction_whenDecoded_thenSetIndexRegisterWithCorrectIndexReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xA300);

        assertThat(instruction).isInstanceOf(SetIndexRegister.class).extracting("i").isEqualTo(0x300);
    }

    @Test
    void given0xBNNNInstruction_whenDecoded_thenJumpWithOffsetReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xB123);

        assertThat(instruction).isInstanceOf(JumpWithOffset.class).extracting("location").isEqualTo(0x123);
    }

    @Test
    void given0xCXNNInstruction_whenDecoded_thenMaskedRandom()
    {
        Runnable instruction = instructionDecoder.decode(0xC489);

        assertThat(instruction).isInstanceOf(MaskedRandom.class)
                .extracting("variableRegister").isEqualTo(0x4);
        assertThat(instruction).isInstanceOf(MaskedRandom.class)
                .extracting("mask").isEqualTo(0x89);
    }

    @Test
    void given0xDXYNInstruction_whenDecoded_thenDrawSpriteReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xD123);

        assertThat(instruction).isInstanceOf(DrawSprite.class);
        assertThat(instruction).extracting("xRegister").isEqualTo(0x1);
        assertThat(instruction).extracting("yRegister").isEqualTo(0x2);
        assertThat(instruction).extracting("height").isEqualTo(0x3);
    }

    @Test
    void given0xEX9EInstruction_whenDecoded_thenSkipIfKeyPressedReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xE49E);

        assertThat(instruction).isInstanceOf(SkipIfKeyPressed.class)
                .extracting("x").isEqualTo(0x4);
    }

    @Test
    void given0xEXA1Instruction_whenDecoded_thenSkipIfKeyNotPressedReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xE8A1);

        assertThat(instruction).isInstanceOf(SkipIfKeyNotPressed.class)
                .extracting("x").isEqualTo(0x8);
    }

    @Test
    void given0xFX07Instruction_whenDecoded_thenSetVariableRegisterFromDelayTimerReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xF907);

        assertThat(instruction).isInstanceOf(SetVariableRegisterFromDelayTimer.class)
                .extracting("x").isEqualTo(0x9);
    }

    @Test
    void given0xFX0AInstruction_whenDecoded_thenWaitForKeyPressReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xFE0A);

        assertThat(instruction).isInstanceOf(WaitForKeyPress.class)
                .extracting("x").isEqualTo(0xE);
    }

    @Test
    void given0xFX15Instruction_whenDecoded_thenSetDelayTimerFromVariableRegisterReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xFA15);

        assertThat(instruction).isInstanceOf(SetDelayTimerFromVariableRegister.class)
                .extracting("x").isEqualTo(0xA);
    }

    @Test
    void given0xFX1EInstruction_whenDecoded_thenAddVariableRegisterToIndexRegisterReturned()
    {
        Runnable instruction = instructionDecoder.decode(0xF41E);

        assertThat(instruction).isInstanceOf(AddVariableRegisterToIndexRegister.class)
                .extracting("x").isEqualTo(0x4);
    }

    @Test
    void given0xFX29Instruction_whenDecoded_thenSetIndexRegisterToFontCharacterLocation()
    {
        Runnable instruction = instructionDecoder.decode(0xFA29);

        assertThat(instruction).isInstanceOf(SetIndexRegisterToFontCharacterLocation.class)
                .extracting("x").isEqualTo(0xA);
    }

    @Test
    void given0xFX33Instruction_whenDecoded_thenStoreVariableRegisterToMemory()
    {
        Runnable instruction = instructionDecoder.decode(0xF533);

        assertThat(instruction).isInstanceOf(StoreVariableRegisterToMemory.class)
                .extracting("x").isEqualTo(0x5);
    }

    @Test
    void given0xFX55Instruction_whenDecoded_thenStoreVariableRegistersToMemory()
    {
        Runnable instruction = instructionDecoder.decode(0xF755);

        assertThat(instruction).isInstanceOf(StoreVariableRegistersToMemory.class)
                .extracting("x").isEqualTo(0x7);
    }

    @Test
    void given0xFX65Instruction_whenDecoded_thenLoadVariableRegistersFromMemory()
    {
        Runnable instruction = instructionDecoder.decode(0xF865);

        assertThat(instruction).isInstanceOf(LoadVariableRegistersFromMemory.class)
                .extracting("x").isEqualTo(0x8);
    }
}
