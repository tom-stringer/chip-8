package chip8.instruction;

import chip8.IndexRegister;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class AddVariableRegisterToIndexRegisterTest
{
    @Test
    void givenVariableRegister_andIndexRegister_whenAddVariableRegisterToIndexRegister_thenIndexRegisterCorrect()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0).set(8);
        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(8);

        new AddVariableRegisterToIndexRegister(variableRegisters, indexRegister, 0).run();

        assertThat(indexRegister.get()).isEqualTo(16);
    }

    @Test
    void givenVariableRegister_andIndexRegister_whenAddVariableRegisterToIndexRegister_thenVariableRegisterUnchanged()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0).set(8);
        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(8);

        new AddVariableRegisterToIndexRegister(variableRegisters, indexRegister, 0).run();

        assertThat(variableRegisters.get(0).get()).isEqualTo(8);
    }
}