package chip8.instruction;

import chip8.VariableRegister;
import chip8.VariableRegisters;

public class NegativeSubtractVariableRegisters implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;
    private final int y;

    public NegativeSubtractVariableRegisters(VariableRegisters variableRegisters, int x, int y) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        VariableRegister variableRegisterX = variableRegisters.get(x);
        int valueX = variableRegisterX.get();
        int valueY = variableRegisters.get(y).get();

        int result = valueY - valueX;
        variableRegisterX.set(result);

        int notBorrow = valueY > valueX ? 1 : 0;
        variableRegisters.get(0xF).set(notBorrow);
    }
}
