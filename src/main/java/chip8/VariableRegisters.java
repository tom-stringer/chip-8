package chip8;

import java.util.stream.IntStream;

public class VariableRegisters {
    private final VariableRegister[] registers;

    public VariableRegisters() {
        registers = IntStream.rangeClosed(0x0, 0xF)
                .mapToObj(x -> new VariableRegister())
                .toArray(VariableRegister[]::new);
    }

    public VariableRegister get(int x) {
        return registers[x];
    }
}
