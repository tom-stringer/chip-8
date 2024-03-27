package chip8.instruction;

import chip8.VariableRegisters;

import java.util.Random;

public class MaskedRandom implements Runnable {
    private final VariableRegisters variableRegisters;
    private final Random random;
    private final int variableRegister;
    private final int mask;

    public MaskedRandom(VariableRegisters variableRegisters, Random random, int variableRegister, int mask) {
        this.variableRegisters = variableRegisters;
        this.random = random;
        this.variableRegister = variableRegister;
        this.mask = mask;
    }

    @Override
    public void run() {
        byte[] bytes = new byte[1];
        random.nextBytes(bytes);
        byte randomByte = bytes[0];

        variableRegisters.get(variableRegister).set(randomByte & mask);
    }
}
