package chip8.instruction;

import chip8.IndexRegister;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class SetIndexRegisterTest
{
    @Test
    void givenIndexRegisterIsZero_whenSetIndexRegister_thenIndexRegisterIsSet()
    {
        IndexRegister indexRegister = new IndexRegister();

        new SetIndexRegister(indexRegister, 0xFFF).run();

        assertThat(indexRegister.get()).isEqualTo(0xFFF);
    }
}