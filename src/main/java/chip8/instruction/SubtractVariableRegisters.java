package chip8.instruction;

import chip8.VariableRegister;
import chip8.VariableRegisters;

public class SubtractVariableRegisters implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;
    private final int y;

    public SubtractVariableRegisters(VariableRegisters variableRegisters, int x, int y) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        VariableRegister variableRegisterX = variableRegisters.get(x);
        int valueX = variableRegisterX.get();
        int valueY = variableRegisters.get(y).get();

        variableRegisterX.subtract(valueY);

        variableRegisters.get(0xF).set(valueX > valueY ? 1 : 0);
    }
}
