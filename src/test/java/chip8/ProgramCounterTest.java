package chip8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ProgramCounterTest {

    @Test
    void givenProgramCounterAtProgramStart_thenProgramCounterIsAtProgramStart()
    {
        ProgramCounter programCounter = new ProgramCounter();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x200);
    }

    @Test
    void givenProgramCounterAtProgramStart_whenGet_thenProgramCounterIsAtProgramStart()
    {
        ProgramCounter programCounter = new ProgramCounter();

        assertThat(programCounter.get()).isEqualTo(0x200);
    }

    @Test
    void givenProgramCounterAtProgramStart_whenGetAndIncrement_thenProgramCounterIsAtProgramStartPlusTwo()
    {
        ProgramCounter programCounter = new ProgramCounter();

        programCounter.getAndIncrement();

        assertThat(programCounter.get()).isEqualTo(0x202);
    }

    @Test
    void givenProgramCounterAtProgramStart_whenSet_thenProgramCounterIsCorrect()
    {
        ProgramCounter programCounter = new ProgramCounter();

        programCounter.set(0);

        assertThat(programCounter.get()).isEqualTo(0);
    }

    @Test
    void givenProgramCounterAtProgramStart_whenSetToMaxMemorySizePlusOne_thenProgramCounterIsZero()
    {
        ProgramCounter programCounter = new ProgramCounter();

        programCounter.set(0x1000);

        assertThat(programCounter.get()).isEqualTo(0);
    }

    @Test
    void givenProgramCounterAtMemoryEndMinusTwo_whenGetAndIncrement_thenProgramCounterIsAtProgramStart()
    {
        ProgramCounter programCounter = new ProgramCounter();
        programCounter.set(4096 - 2);

        programCounter.getAndIncrement();

        assertThat(programCounter.get()).isEqualTo(0x200);
    }
}