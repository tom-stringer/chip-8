package chip8;

import org.junit.jupiter.api.Test;

import java.util.stream.IntStream;

import static org.assertj.core.api.Assertions.*;

class StackTest
{
    @Test
    void givenStack_andEmpty_whenPushProgramCounter_thenProgramCounterUnchanged()
    {
        ProgramCounter programCounter = new ProgramCounter();
        programCounter.set(7);
        Stack stack = new Stack(programCounter);

        stack.pushProgramCounter();

        assertThat(programCounter.getAndIncrement()).isEqualTo(7);
    }

    @Test
    void givenStack_andProgramCounterPushed_whenPopProgramCounter_thenProgramCounterIsCorrect()
    {
        ProgramCounter programCounter = new ProgramCounter();
        programCounter.set(7);
        Stack stack = new Stack(programCounter);
        stack.pushProgramCounter();
        programCounter.set(0);

        stack.popProgramCounter();

        assertThat(programCounter.getAndIncrement()).isEqualTo(7);
    }

    @Test
    void givenStack_andEmpty_whenPopProgramCounter_thenExceptionThrown()
    {
        ProgramCounter programCounter = new ProgramCounter();
        Stack stack = new Stack(programCounter);

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(stack::popProgramCounter);
    }

    @Test
    void givenStack_andFull_whenPushProgramCounter_thenExceptionThrown()
    {
        ProgramCounter programCounter = new ProgramCounter();
        Stack stack = new Stack(programCounter);

        for (int i = 0; i < 16; i++) {
            stack.pushProgramCounter();
            programCounter.getAndIncrement();
        }

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(stack::pushProgramCounter);
    }

    @Test
    void givenStack_andFull_whenPopProgramCounterSixteenTimes_thenProgramCounterIsCorrect()
    {
        ProgramCounter programCounter = new ProgramCounter();
        Stack stack = new Stack(programCounter);

        for (int i = 0; i < 16; i++) {
            stack.pushProgramCounter();
            programCounter.getAndIncrement();
        }

        int[] array = IntStream.range(0, 16).peek(i -> stack.popProgramCounter()).map(i -> programCounter.get()).toArray();

        assertThat(array).containsExactly(0x21E, 0x21C, 0x21A, 0x218, 0x216, 0x214, 0x212, 0x210, 0x20E, 0x20C, 0x20A, 0x208, 0x206, 0x204, 0x202, 0x200);
    }

    @Test
    void givenStack_andFull_whenPopProgramCounterSeventeenTimes_thenExceptionThrown()
    {
        ProgramCounter programCounter = new ProgramCounter();
        Stack stack = new Stack(programCounter);

        for (int i = 0; i < 16; i++) {
            stack.pushProgramCounter();
            programCounter.getAndIncrement();
        }

        for (int i = 0; i < 16; i++) {
            stack.popProgramCounter();
        }

        assertThatExceptionOfType(IllegalStateException.class).isThrownBy(stack::popProgramCounter);
    }
}