package chip8;

import chip8.display.DisplayModel;
import chip8.input.Keyboard;
import chip8.decoder.InstructionDecoder;
import chip8.timer.DelayTimer;

import java.util.Random;

public class FetchDecodeExecutor
{
    private final Memory memory;
    private final ProgramCounter programCounter;
    private final InstructionDecoder instructionDecoder;

    public FetchDecodeExecutor(Memory memory, ProgramCounter programCounter, Stack stack, DisplayModel displayModel, IndexRegister indexRegister, VariableRegisters variableRegisters, Keyboard keyboard, DelayTimer delayTimer, Random random)
    {
        this.memory = memory;
        this.programCounter = programCounter;
        this.instructionDecoder = new InstructionDecoder(programCounter, stack, indexRegister, variableRegisters, displayModel, memory, keyboard, delayTimer, random);
    }

    public void fetchDecodeExecute()
    {
        int location = programCounter.getAndIncrement();

        byte byte1 = memory.get(location);
        byte byte2 = memory.get(location + 1);

        int instruction = byte1 << 8 | byte2 & 0xFF;

        instructionDecoder.decode(instruction).run();
    }
}
