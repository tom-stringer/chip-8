package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class StoreVariableRegistersToMemoryTest
{
    @Test
    void givenVariableRegistersSet_andIndexRegister_andMemory_whenStoreVariableRegistersToMemory_thenVariableRegistersStoredInMemory()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0).set(123);
        variableRegisters.get(1).set(234);
        variableRegisters.get(2).set(0);
        variableRegisters.get(3).set(0xFF);

        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(10);

        Memory memory = new Memory();

        new StoreVariableRegistersToMemory(variableRegisters, indexRegister, memory, 3).run();

        assertThat(memory.get(10)).isEqualTo((byte) 123);
        assertThat(memory.get(11)).isEqualTo((byte) 234);
        assertThat(memory.get(12)).isEqualTo((byte) 0);
        assertThat(memory.get(13)).isEqualTo((byte) 0xFF);
    }

    @Test
    void givenVariableRegistersSet_andIndexRegister_andMemory_whenStoreVariableRegistersToMemory_thenIndexRegisterUpdatedCorrectly()
    {
        VariableRegisters variableRegisters = new VariableRegisters();
        variableRegisters.get(0).set(123);
        variableRegisters.get(1).set(234);
        variableRegisters.get(2).set(0);
        variableRegisters.get(3).set(0xFF);

        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(11);

        Memory memory = new Memory();

        new StoreVariableRegistersToMemory(variableRegisters, indexRegister, memory, 3).run();

        assertThat(indexRegister.get()).isEqualTo(15);
    }
}