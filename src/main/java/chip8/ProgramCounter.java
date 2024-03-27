package chip8;

public class ProgramCounter {
    static final int PROGRAM_START = 0x200;

    private int pc;

    public ProgramCounter() {
        pc = PROGRAM_START;
    }

    public int getAndIncrement() {
        int currentPc = pc;

        if (currentPc >= Memory.SIZE - 2) {
            pc = PROGRAM_START;
        } else {
            pc += 2;
        }

        return currentPc;
    }

    public int get() {
        return pc;
    }

    public void set(int pc) {
        this.pc = pc & 0xFFF;
    }
}
