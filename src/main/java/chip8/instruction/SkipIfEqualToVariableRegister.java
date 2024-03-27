package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegister;
import chip8.VariableRegisters;

public class SkipIfEqualToVariableRegister implements Runnable {
    private final VariableRegisters variableRegisters;
    private final ProgramCounter programCounter;
    private final int x;
    private final int n;

    public SkipIfEqualToVariableRegister(VariableRegisters variableRegisters, ProgramCounter programCounter, int x, int n) {
        this.variableRegisters = variableRegisters;
        this.programCounter = programCounter;
        this.x = x;
        this.n = n;
    }

    @Override
    public void run() {
        VariableRegister variableRegister = variableRegisters.get(x);

        if (variableRegister.get() == n)
        {
            programCounter.getAndIncrement();
        }
    }
}
