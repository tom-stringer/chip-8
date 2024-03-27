package chip8.instruction;

import chip8.ProgramCounter;
import chip8.VariableRegisters;
import chip8.input.Keyboard;

public class SkipIfKeyPressed implements Runnable {

    private final ProgramCounter programCounter;
    private final VariableRegisters variableRegisters;
    private final Keyboard keyboard;
    private final int x;

    public SkipIfKeyPressed(ProgramCounter programCounter, VariableRegisters variableRegisters, Keyboard keyboard, int x) {
        this.programCounter = programCounter;
        this.variableRegisters = variableRegisters;
        this.keyboard = keyboard;
        this.x = x;
    }

    @Override
    public void run() {
        keyboard.getPressedKey().ifPresent(key -> {
            int expectedKey = variableRegisters.get(x).get();

            if (expectedKey == key)
            {
                programCounter.getAndIncrement();
            }
        });
    }
}
