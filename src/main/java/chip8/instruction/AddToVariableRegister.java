package chip8.instruction;

import chip8.VariableRegisters;

public class AddToVariableRegister implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;
    private final int k;

    public AddToVariableRegister(VariableRegisters variableRegisters, int x, int k) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.k = k;
    }

    @Override
    public void run() {
        variableRegisters.get(x).add(k);
    }
}
