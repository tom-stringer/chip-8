package chip8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class IndexRegisterTest
{
    @Test
    void givenIndexRegister_thenValueIsZero()
    {
        IndexRegister indexRegister = new IndexRegister();

        assertThat(indexRegister.get()).isZero();
    }

    @Test
    void givenIndexRegister_whenSet_thenValueIsUpdated()
    {
        IndexRegister indexRegister = new IndexRegister();

        indexRegister.set(0x300);

        assertThat(indexRegister.get()).isEqualTo(0x300);
    }

    @Test
    void givenIndexRegister_whenSetOverflow_thenValueIsUpdatedCorrectly()
    {
        IndexRegister indexRegister = new IndexRegister();

        indexRegister.set(0x10000);

        assertThat(indexRegister.get()).isZero();
    }
}
