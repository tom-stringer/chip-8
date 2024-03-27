package chip8.instruction;

import chip8.ProgramCounter;

public class Jump implements Runnable
{
    private final ProgramCounter programCounter;
    private final int location;

    public Jump(ProgramCounter programCounter, int location)
    {
        this.programCounter = programCounter;
        this.location = location;
    }

    @Override
    public void run()
    {
        programCounter.set(location);
    }
}
