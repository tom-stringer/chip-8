package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import chip8.input.Keyboard;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

class SkipIfKeyNotPressedTest {

    @Test
    void givenNoKeyPressed_whenSkipIfKeyNotPressed_thenProgramCounterIncremented()
    {
        ProgramCounter programCounter = new ProgramCounter();
        VariableRegisters variableRegisters = new VariableRegisters();
        Keyboard keyboard = new Keyboard();
        SkipIfKeyNotPressed skipIfKeyPressed = new SkipIfKeyNotPressed(programCounter, variableRegisters, keyboard, 0x1);

        skipIfKeyPressed.run();

        Assertions.assertThat(programCounter.get()).isEqualTo(0x202);
    }

    @Test
    void givenKeyPressed_whenSkipIfKeyNotPressedWithGivenKey_thenProgramCounterNotIncremented()
    {
        ProgramCounter programCounter = new ProgramCounter();
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0x2).set(0xF);
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(0xF);
        SkipIfKeyNotPressed skipIfKeyPressed = new SkipIfKeyNotPressed(programCounter, variableRegisters, keyboard, 0x2);

        skipIfKeyPressed.run();

        Assertions.assertThat(programCounter.get()).isEqualTo(0x200);
    }

    @Test
    void givenKeyPressed_whenSkipIfKeyNotPressedWithDifferentKey_thenProgramCounterIncremented()
    {
        ProgramCounter programCounter = new ProgramCounter();
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0x3).set(0xF);
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(0xA);
        SkipIfKeyNotPressed skipIfKeyPressed = new SkipIfKeyNotPressed(programCounter, variableRegisters, keyboard, 0x3);

        skipIfKeyPressed.run();

        Assertions.assertThat(programCounter.get()).isEqualTo(0x202);
    }
}