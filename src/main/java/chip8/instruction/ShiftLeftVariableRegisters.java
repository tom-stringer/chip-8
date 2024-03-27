package chip8.instruction;

import chip8.VariableRegister;
import chip8.VariableRegisters;

public class ShiftLeftVariableRegisters implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;

    // TODO: Quirks mode.
    @SuppressWarnings("FieldCanBeLocal")
    private final int y;

    public ShiftLeftVariableRegisters(VariableRegisters variableRegisters, int x, int y) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        VariableRegister variableRegisterX = variableRegisters.get(x);
        int valueX = variableRegisterX.get();

        variableRegisterX.set(valueX << 1);

        variableRegisters.get(0xF).set((valueX & 0b1000_0000) >> 7);
    }
}
