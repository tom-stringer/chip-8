package chip8;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VariableRegisterTest
{
    @Test
    void givenVariableRegister_thenValueIsZero()
    {
        VariableRegister variableRegister = new VariableRegister();

        assertThat(variableRegister.get()).isZero();
    }

    @Test
    void givenVariableRegister_whenSet_thenValueIsUpdated()
    {
        VariableRegister variableRegister = new VariableRegister();

        variableRegister.set(0x30);

        assertThat(variableRegister.get()).isEqualTo(0x30);
    }

    @Test
    void givenVariableRegister_whenSetWithOverflow_thenValueIsUpdatedCorrectly()
    {
        VariableRegister variableRegister = new VariableRegister();

        variableRegister.set(0x123);

        assertThat(variableRegister.get()).isEqualTo(0x23);
    }

    @Test
    void givenVariableRegister_whenAdd_thenValueIsUpdatedCorrectly()
    {
        VariableRegister variableRegister = new VariableRegister();

        variableRegister.add(0x30);

        assertThat(variableRegister.get()).isEqualTo(0x30);
    }

    @Test
    void givenVariableRegister_whenAddWithOverflow_thenValueIsUpdatedCorrectly()
    {
        VariableRegister variableRegister = new VariableRegister();
        variableRegister.set(0xFF);

        variableRegister.add(0x30);

        assertThat(variableRegister.get()).isEqualTo(0x2F);
    }

    @Test
    void givenVariableRegister_whenSubtract_thenValueIsUpdatedCorrectly()
    {
        VariableRegister variableRegister = new VariableRegister();
        variableRegister.set(0xFF);

        variableRegister.subtract(1);

        assertThat(variableRegister.get()).isEqualTo(0xFE);
    }

    @Test
    void givenVariableRegister_whenSubtractWithOverflow_thenValueIsUpdatedCorrectly()
    {
        VariableRegister variableRegister = new VariableRegister();

        variableRegister.subtract(1);

        assertThat(variableRegister.get()).isEqualTo(0xFF);
    }
}
