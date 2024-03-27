package chip8.instruction;

import chip8.ProgramCounter;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class JumpTest
{
    @Test
    void givenProgramCounterSetAtProgramStart_whenJumpToLocation_thenProgramCounterSetToLocation()
    {
        ProgramCounter programCounter = new ProgramCounter();

        new Jump(programCounter, 0x300).run();

        assertThat(programCounter.getAndIncrement()).isEqualTo(0x300);
    }
}