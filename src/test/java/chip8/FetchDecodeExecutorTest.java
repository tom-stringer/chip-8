package chip8;

import chip8.display.DisplayModel;
import chip8.input.Keyboard;
import chip8.timer.DelayTimer;
import org.junit.jupiter.api.Test;

import java.util.Random;
import java.util.concurrent.Executors;

import static org.assertj.core.api.Assertions.assertThat;

class FetchDecodeExecutorTest
{
    @Test
    void givenFetchDecodeExecutor_whenFetchDecodeExecute_thenProgramCounterIncrementedByTwo()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();
        ProgramCounter programCounter = new ProgramCounter();
        Stack stack = new Stack(programCounter);
        Keyboard keyboard = new Keyboard();
        DelayTimer delayTimer = new DelayTimer(Executors.newSingleThreadExecutor());
        Random random = new Random();

        FetchDecodeExecutor fetchDecodeExecutor = new FetchDecodeExecutor(memory, programCounter, stack, displayModel, indexRegister, variableRegisters, keyboard, delayTimer, random);
        fetchDecodeExecutor.fetchDecodeExecute();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x202);
    }

    @Test
    void givenFetchDecodeExecutor_whenFetchDecodeExecute_thenInstructionIsExecuted()
    {
        Memory memory = new Memory();
        memory.loadProgram(new byte[] { (byte) 0xA1, (byte) 0x23 });
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();
        ProgramCounter programCounter = new ProgramCounter();
        Stack stack = new Stack(programCounter);
        Keyboard keyboard = new Keyboard();
        DelayTimer delayTimer = new DelayTimer(Executors.newSingleThreadExecutor());
        Random random = new Random();

        FetchDecodeExecutor fetchDecodeExecutor = new FetchDecodeExecutor(memory, programCounter, stack, displayModel, indexRegister, variableRegisters, keyboard, delayTimer, random);
        fetchDecodeExecutor.fetchDecodeExecute();

        assertThat(indexRegister.get()).isEqualTo(0x123);
    }
}