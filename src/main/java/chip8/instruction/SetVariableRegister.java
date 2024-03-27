package chip8.instruction;

import chip8.VariableRegisters;

public class SetVariableRegister implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;
    private final int k;

    public SetVariableRegister(VariableRegisters variableRegisters, int x, int k) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.k = k;
    }

    @Override
    public void run() {
        variableRegisters.get(x).set(k);
    }
}
