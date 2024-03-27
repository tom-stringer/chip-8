package chip8.instruction;

import chip8.VariableRegisters;
import chip8.input.Keyboard;

public class WaitForKeyPress implements Runnable {
    private final Keyboard keyboard;
    private final VariableRegisters variableRegisters;
    private final int x;

    public WaitForKeyPress(Keyboard keyboard, VariableRegisters variableRegisters, int x) {
        this.keyboard = keyboard;
        this.variableRegisters = variableRegisters;
        this.x = x;
    }

    @Override
    public void run() {
        int key = keyboard.waitForKeyPress();

        variableRegisters.get(x).set(key);
    }
}
