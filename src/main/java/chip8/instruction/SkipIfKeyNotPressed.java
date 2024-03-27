package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import chip8.input.Keyboard;

public class SkipIfKeyNotPressed implements Runnable {
    private final ProgramCounter programCounter;
    private final VariableRegisters variableRegisters;
    private final Keyboard keyboard;
    private final int x;

    public SkipIfKeyNotPressed(ProgramCounter programCounter, VariableRegisters variableRegisters, Keyboard keyboard, int x) {
        this.programCounter = programCounter;
        this.variableRegisters = variableRegisters;
        this.keyboard = keyboard;
        this.x = x;
    }

    @Override
    public void run() {
        keyboard.getPressedKey().ifPresentOrElse(
                key -> {
                    int expectedKey = variableRegisters.get(x).get();

                    if (expectedKey != key) {
                        programCounter.getAndIncrement();
                    }
                },
                programCounter::getAndIncrement
        );
    }
}
