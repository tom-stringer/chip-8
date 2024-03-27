package chip8.instruction;

import chip8.VariableRegister;
import chip8.VariableRegisters;

public class ShiftRightVariableRegisters implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;

    // TODO: Quirks mode.
    @SuppressWarnings("FieldCanBeLocal")
    private final int y;

    public ShiftRightVariableRegisters(VariableRegisters variableRegisters, int x, int y) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        VariableRegister variableRegisterX = variableRegisters.get(x);
        int valueX = variableRegisterX.get();

        int result = valueX >> 1;
        variableRegisterX.set(result);

        variableRegisters.get(0xF).set(valueX & 0b1);
    }
}
