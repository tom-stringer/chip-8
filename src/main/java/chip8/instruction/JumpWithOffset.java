package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;

import javax.swing.text.html.HTMLDocument;

public class JumpWithOffset implements Runnable
{
    private final ProgramCounter programCounter;
    private final VariableRegisters variableRegisters;
    private final int location;

    public JumpWithOffset(ProgramCounter programCounter, VariableRegisters variableRegisters, int location)
    {
        this.programCounter = programCounter;
        this.variableRegisters = variableRegisters;
        this.location = location;
    }

    @Override
    public void run()
    {
        int offset = variableRegisters.get(0).get();

        programCounter.set(location + offset);
    }
}
