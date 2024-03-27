package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegisters;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class LoadVariableRegistersFromMemoryTest {

    @Test
    void givenMemorySet_andIndexRegister_andVariableRegisters_whenLoadVariableRegistersFromMemory_thenVariableRegistersSet()
    {
        Memory memory = new Memory();
        memory.set(10, (byte) 123);
        memory.set(11, (byte) 234);
        memory.set(12, (byte) 0);
        memory.set(13, (byte) 0xFF);

        VariableRegisters variableRegisters = new VariableRegisters();

        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(10);

        new LoadVariableRegistersFromMemory(variableRegisters, indexRegister, memory, 3).run();

        assertThat(variableRegisters.get(0).get()).isEqualTo(123);
        assertThat(variableRegisters.get(1).get()).isEqualTo(234);
        assertThat(variableRegisters.get(2).get()).isEqualTo(0);
        assertThat(variableRegisters.get(3).get()).isEqualTo(0xFF);
    }

    @Test
    void givenMemorySet_andIndexRegister_andVariableRegisters_whenLoadVariableRegistersFromMemory_thenIndexRegisterUpdatedCorrectly()
    {
        Memory memory = new Memory();
        memory.set(10, (byte) 123);
        memory.set(11, (byte) 234);
        memory.set(12, (byte) 0);
        memory.set(13, (byte) 0xFF);

        VariableRegisters variableRegisters = new VariableRegisters();

        IndexRegister indexRegister = new IndexRegister();
        indexRegister.set(11);

        new LoadVariableRegistersFromMemory(variableRegisters, indexRegister, memory, 3).run();

        assertThat(indexRegister.get()).isEqualTo(15);
    }
}