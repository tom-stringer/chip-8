package chip8.instruction;

import chip8.VariableRegisters;
import chip8.timer.DelayTimer;

public class SetVariableRegisterFromDelayTimer implements Runnable {

    private final VariableRegisters variableRegisters;
    private final DelayTimer delayTimer;
    private final int x;

    public SetVariableRegisterFromDelayTimer(VariableRegisters variableRegisters, DelayTimer delayTimer, int x) {
        this.variableRegisters = variableRegisters;
        this.delayTimer = delayTimer;
        this.x = x;
    }

    @Override
    public void run() {
        variableRegisters.get(x).set(delayTimer.get());
    }
}
