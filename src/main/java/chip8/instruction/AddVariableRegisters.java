package chip8.instruction;

import chip8.VariableRegister;
import chip8.VariableRegisters;

public class AddVariableRegisters implements Runnable {

    private final VariableRegisters variableRegisters;
    private final int x;
    private final int y;

    public AddVariableRegisters(VariableRegisters variableRegisters, int x, int y) {
        this.variableRegisters = variableRegisters;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        VariableRegister variableRegisterX = variableRegisters.get(x);
        int valueX = variableRegisterX.get();
        int valueY = variableRegisters.get(y).get();

        variableRegisterX.add(valueY);

        int result = valueX + valueY;
        variableRegisters.get(0xF).set((result & 0xFF) == result ? 0 : 1);
    }
}
