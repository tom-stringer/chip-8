package chip8.instruction;

import chip8.IndexRegister;
import chip8.VariableRegisters;

public class AddVariableRegisterToIndexRegister implements Runnable {

    private final VariableRegisters variableRegisters;
    private final IndexRegister indexRegister;
    private final int x;

    public AddVariableRegisterToIndexRegister(VariableRegisters variableRegisters, IndexRegister indexRegister, int x)
    {
        this.variableRegisters = variableRegisters;
        this.indexRegister = indexRegister;
        this.x = x;
    }

    @Override
    public void run() {
        indexRegister.set(indexRegister.get() + variableRegisters.get(x).get());
    }
}
