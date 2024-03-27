package chip8;

import chip8.display.DisplayModel;
import chip8.display.DisplayView;
import chip8.input.Keyboard;
import chip8.timer.DelayTimer;
import chip8.ui.Display;
import org.junit.jupiter.api.Test;

import java.util.Random;

import static org.assertj.core.api.Assertions.assertThat;

class Chip8ConfigurationTest
{
    private static final int INSTRUCTION_PER_SECOND = 700;

    @Test
    void givenMemoryNotRetrieved_whenRetrieveMemory_thenMemoryNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Memory memory = chip8Configuration.memory();

        assertThat(memory).isNotNull();
    }

    @Test
    void givenMemoryRetrieved_whenRetrieveMemory_thenMemoryIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Memory memory = chip8Configuration.memory();

        assertThat(chip8Configuration.memory()).isSameAs(memory);
    }

    @Test
    void givenProgramCounterNotRetrieved_whenRetrieveProgramCounter_thenProgramCounterNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        ProgramCounter programCounter = chip8Configuration.programCounter();

        assertThat(programCounter).isNotNull();
    }

    @Test
    void givenProgramCounterRetrieved_whenRetrieveProgramCounter_thenProgramCounterIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        ProgramCounter programCounter = chip8Configuration.programCounter();

        assertThat(chip8Configuration.programCounter()).isSameAs(programCounter);
    }

    @Test
    void givenStackNotRetrieved_whenRetrieveStack_thenStackNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Stack stack = chip8Configuration.stack();

        assertThat(stack).isNotNull();
    }

    @Test
    void givenStackRetrieved_whenRetrieveStack_thenStackIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Stack stack = chip8Configuration.stack();

        assertThat(chip8Configuration.stack()).isSameAs(stack);
    }

    @Test
    void givenIndexRegisterNotRetrieved_whenRetrieveIndexRegister_thenIndexRegisterNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        IndexRegister indexRegister = chip8Configuration.indexRegister();

        assertThat(indexRegister).isNotNull();
    }

    @Test
    void givenIndexRegisterRetrieved_whenRetrieveIndexRegister_thenIndexRegisterIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        IndexRegister indexRegister = chip8Configuration.indexRegister();

        assertThat(chip8Configuration.indexRegister()).isSameAs(indexRegister);
    }

    @Test
    void givenVariableRegistersNotRetrieved_whenRetrieveVariableRegisters_thenVariableRegistersNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        VariableRegisters variableRegisters = chip8Configuration.variableRegisters();

        assertThat(variableRegisters).isNotNull();
    }

    @Test
    void givenVariableRegistersRetrieved_whenRetrieveVariableRegisters_thenVariableRegistersIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        VariableRegisters variableRegisters = chip8Configuration.variableRegisters();

        assertThat(chip8Configuration.variableRegisters()).isSameAs(variableRegisters);
    }

    // TODO: display presenter test

    @Test
    void givenDisplayModelNotRetrieved_whenRetrieveDisplayModel_thenDisplayModelNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        DisplayModel displayModel = chip8Configuration.displayModel();

        assertThat(displayModel).isNotNull();
    }

    @Test
    void givenDisplayModelRetrieved_whenRetrieveDisplayModel_thenDisplayModelIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        DisplayModel displayModel = chip8Configuration.displayModel();

        assertThat(chip8Configuration.displayModel()).isSameAs(displayModel);
    }

    @Test
    void givenDisplayViewNotRetrieved_whenRetrieveDisplayView_thenDisplayViewNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        DisplayView display = chip8Configuration.display();

        assertThat(display).isNotNull();
    }

    @Test
    void givenDisplayViewRetrieved_whenRetrieveDisplayView_thenDisplayViewIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        DisplayView display = chip8Configuration.display();

        assertThat(chip8Configuration.display()).isSameAs(display);
    }

    @Test
    void givenKeyboardNotRetrieved_whenRetrieveKeyboard_thenKeyboardNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Keyboard keyboard = chip8Configuration.keyboard();

        assertThat(keyboard).isNotNull();
    }

    @Test
    void givenKeyboardRetrieved_whenRetrieveKeyboard_thenKeyboardIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Keyboard keyboard = chip8Configuration.keyboard();

        assertThat(chip8Configuration.keyboard()).isSameAs(keyboard);
    }

    @Test
    void givenDelayTimerNotRetrieved_whenRetrieveDelayTimer_thenDelayTimerNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        DelayTimer delayTimer = chip8Configuration.delayTimer();

        assertThat(delayTimer).isNotNull();
    }

    @Test
    void givenDelayTimerRetrieved_whenRetrieveDelayTimer_thenDelayTimerIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        DelayTimer delayTimer = chip8Configuration.delayTimer();

        assertThat(chip8Configuration.delayTimer()).isSameAs(delayTimer);
    }

    @Test
    void givenRandomNotRetrieved_whenRetrieveRandom_thenRandomNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Random random = chip8Configuration.random();

        assertThat(random).isNotNull();
    }

    @Test
    void givenRandomRetrieved_whenRetrieveRandom_thenRandomIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Random random = chip8Configuration.random();

        assertThat(chip8Configuration.random()).isSameAs(random);
    }

    @Test
    void givenFetchDecodeExecutorNotRetrieved_whenRetrieveFetchDecodeExecutor_thenFetchDecodeExecutorNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        FetchDecodeExecutor fetchDecodeExecutor = chip8Configuration.fetchDecodeExecutor();

        assertThat(fetchDecodeExecutor).isNotNull();
    }

    @Test
    void givenFetchDecodeExecutorRetrieved_whenRetrieveFetchDecodeExecutor_thenFetchDecodeExecutorIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        FetchDecodeExecutor fetchDecodeExecutor = chip8Configuration.fetchDecodeExecutor();

        assertThat(chip8Configuration.fetchDecodeExecutor()).isSameAs(fetchDecodeExecutor);
    }

    @Test
    void givenEmulatorNotRetrieved_whenRetrieveEmulator_thenEmulatorNotNull()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Emulator emulator = chip8Configuration.emulator();

        assertThat(emulator).isNotNull();
    }

    @Test
    void givenEmulatorRetrieved_whenRetrieveEmulator_thenEmulatorIsSameInstance()
    {
        Chip8Configuration chip8Configuration = new Chip8Configuration(INSTRUCTION_PER_SECOND, Display::new);

        Emulator emulator = chip8Configuration.emulator();

        assertThat(chip8Configuration.emulator()).isSameAs(emulator);
    }
}