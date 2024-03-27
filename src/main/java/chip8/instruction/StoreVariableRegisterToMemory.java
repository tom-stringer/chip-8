package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegisters;

public class StoreVariableRegisterToMemory implements Runnable {

    private final VariableRegisters variableRegisters;
    private final IndexRegister indexRegister;
    private final Memory memory;
    private final int x;

    public StoreVariableRegisterToMemory(VariableRegisters variableRegisters, IndexRegister indexRegister, Memory memory, int x)
    {
        this.variableRegisters = variableRegisters;
        this.indexRegister = indexRegister;
        this.memory = memory;
        this.x = x;
    }

    @Override
    public void run()
    {
        int value = variableRegisters.get(x).get();
        int index = indexRegister.get();

        int hundreds = value / 100;
        int tens = (value % 100) / 10;
        int ones = value % 10;

        memory.set(index, (byte) hundreds);
        memory.set(index + 1, (byte) tens);
        memory.set(index + 2, (byte) ones);
    }
}
