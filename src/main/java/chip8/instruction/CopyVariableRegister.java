package chip8.instruction;

import chip8.VariableRegisters;

public class CopyVariableRegister implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;
    private final int y;

    public CopyVariableRegister(VariableRegisters variableRegisters, int x, int y) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        int value = variableRegisters.get(y).get();
        variableRegisters.get(x).set(value);
    }
}
