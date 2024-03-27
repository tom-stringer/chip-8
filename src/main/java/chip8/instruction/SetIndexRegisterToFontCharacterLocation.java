package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegister;
import chip8.VariableRegisters;

public class SetIndexRegisterToFontCharacterLocation implements Runnable
{
    private final VariableRegisters variableRegisters;
    private final IndexRegister indexRegister;
    private final int x;

    public SetIndexRegisterToFontCharacterLocation(VariableRegisters variableRegisters, IndexRegister indexRegister, int x)
    {
        this.variableRegisters = variableRegisters;
        this.indexRegister = indexRegister;
        this.x = x;
    }

    @Override
    public void run()
    {
        VariableRegister variableRegister = variableRegisters.get(x);
        int character = variableRegister.get();
        int offset = character * 5;

        indexRegister.set(Memory.FONT_LOCATION + offset);
    }
}
