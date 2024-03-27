package chip8.instruction;

import chip8.ProgramCounter;
import chip8.Stack;

public class Call implements Runnable {
    private final ProgramCounter programCounter;
    private final Stack stack;
    private final int location;

    public Call(ProgramCounter programCounter, Stack stack, int location) {
        this.programCounter = programCounter;
        this.stack = stack;

        this.location = location;
    }

    @Override
    public void run() {
        stack.pushProgramCounter();

        programCounter.set(location);
    }
}
