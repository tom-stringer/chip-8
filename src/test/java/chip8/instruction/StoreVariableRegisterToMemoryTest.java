package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StoreVariableRegisterToMemoryTest
{
    @Test
    void givenVariableRegisterSet_andIndexRegister_andMemory_whenStoreVariableRegisterToMemory_thenBinaryCodedDecimalStoredInCorrectMemoryLocations()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(3).set(123);

        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(7);

        Memory memory = new Memory();

        new StoreVariableRegisterToMemory(variableRegisters, indexRegister, memory, 3).run();

        assertThat(memory.get(7)).isEqualTo((byte) 1);
        assertThat(memory.get(8)).isEqualTo((byte) 2);
        assertThat(memory.get(9)).isEqualTo((byte) 3);
    }

    @Test
    void givenVariableRegisterSetToZero_andIndexRegister_andMemory_whenStoreVariableRegisterToMemory_thenBinaryCodedDecimalStoredInCorrectMemoryLocations()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(3).set(0);

        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(8);

        Memory memory = new Memory();
        memory.set(8, (byte) 0xFF);
        memory.set(9, (byte) 0xFF);
        memory.set(10, (byte) 0xFF);

        new StoreVariableRegisterToMemory(variableRegisters, indexRegister, memory, 3).run();

        assertThat(memory.get(8)).isEqualTo((byte) 0);
        assertThat(memory.get(9)).isEqualTo((byte) 0);
        assertThat(memory.get(10)).isEqualTo((byte) 0);
    }

    @Test
    void givenVariableRegisterSetToMaximumValue_andIndexRegister_andMemory_whenStoreVariableRegisterToMemory_thenBinaryCodedDecimalStoredInCorrectMemoryLocations()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(3).set(0xFF);

        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(9);

        Memory memory = new Memory();

        new StoreVariableRegisterToMemory(variableRegisters, indexRegister, memory, 3).run();

        assertThat(memory.get(9)).isEqualTo((byte) 2);
        assertThat(memory.get(10)).isEqualTo((byte) 5);
        assertThat(memory.get(11)).isEqualTo((byte) 5);
    }
}