package chip8;

public class Stack {
    private final ProgramCounter programCounter;
    private final int[] programCounters;

    private int stackPointer;

    public Stack(ProgramCounter programCounter) {

        this.programCounter = programCounter;
        this.programCounters = new int[16];
    }

    public void pushProgramCounter() {
        if (stackPointer == programCounters.length)
        {
            throw new IllegalStateException();
        }

        programCounters[stackPointer++] = programCounter.get();
    }

    public void popProgramCounter() {
        if (stackPointer == 0)
        {
            throw new IllegalStateException();
        }

        programCounter.set(programCounters[--stackPointer]);
    }
}
