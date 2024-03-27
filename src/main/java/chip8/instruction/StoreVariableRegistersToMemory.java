package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegisters;

public class StoreVariableRegistersToMemory implements Runnable {

    private final VariableRegisters variableRegisters;
    private final IndexRegister indexRegister;
    private final Memory memory;
    private final int x;

    public StoreVariableRegistersToMemory(VariableRegisters variableRegisters, IndexRegister indexRegister, Memory memory, int x)
    {
        this.variableRegisters = variableRegisters;
        this.indexRegister = indexRegister;
        this.memory = memory;
        this.x = x;
    }

    @Override
    public void run() {
        int index = indexRegister.get();

        for (int i = 0; i <= x; i++)
        {
            byte value = (byte) variableRegisters.get(i).get();

            memory.set(index + i, value);
        }

        indexRegister.set(index + x + 1);
    }
}
