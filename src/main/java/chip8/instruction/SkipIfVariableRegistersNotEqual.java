package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegister;
import chip8.VariableRegisters;

public class SkipIfVariableRegistersNotEqual implements Runnable {
    private final VariableRegisters variableRegisters;
    private final ProgramCounter programCounter;
    private final int x;
    private final int y;

    public SkipIfVariableRegistersNotEqual(VariableRegisters variableRegisters, ProgramCounter programCounter, int x, int y) {
        this.variableRegisters = variableRegisters;
        this.programCounter = programCounter;
        this.x = x;
        this.y = y;
    }

    @Override
    public void run() {
        VariableRegister variableRegisterX = variableRegisters.get(x);
        VariableRegister variableRegisterY = variableRegisters.get(y);

        if (variableRegisterX.get() != variableRegisterY.get())
        {
            programCounter.getAndIncrement();
        }
    }
}
