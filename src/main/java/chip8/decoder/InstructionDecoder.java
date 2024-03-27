package chip8.decoder;

import chip8.*;
import chip8.display.DisplayModel;
import chip8.input.Keyboard;
import chip8.instruction.*;
import chip8.timer.DelayTimer;

import java.util.Random;

public class InstructionDecoder
{
    private static final Runnable NOOP = () -> {};

    private final ProgramCounter programCounter;
    private final Stack stack;
    private final IndexRegister indexRegister;
    private final VariableRegisters variableRegisters;
    private final DisplayModel displayModel;
    private final Memory memory;
    private final Keyboard keyboard;
    private final DelayTimer delayTimer;
    private final Random random;

    public InstructionDecoder(ProgramCounter programCounter,
                              Stack stack,
                              IndexRegister indexRegister,
                              VariableRegisters variableRegisters,
                              DisplayModel displayModel,
                              Memory memory,
                              Keyboard keyboard,
                              DelayTimer delayTimer, Random random)
    {
        this.programCounter = programCounter;
        this.stack = stack;
        this.indexRegister = indexRegister;
        this.variableRegisters = variableRegisters;
        this.displayModel = displayModel;
        this.memory = memory;
        this.keyboard = keyboard;
        this.delayTimer = delayTimer;
        this.random = random;
    }

    public Runnable decode(int instruction)
    {
        if (instruction == 0x00E0)
        {
            return new ClearDisplay(displayModel);
        }

        if (instruction == 0x00EE)
        {
            return new Return(stack);
        }

        int opCode = (instruction & 0xF000) >> 12;

        return switch (opCode) {
            case 0x1 -> decodeJump(instruction);
            case 0x2 -> decodeCall(instruction);
            case 0x3 -> decodeSkipIfEqualToVariableRegister(instruction);
            case 0x4 -> decodeSkipIfNotEqualToVariableRegister(instruction);
            case 0x5 -> decodeSkipIfVariableRegistersEqual(instruction);
            case 0x6 -> decodeSetVariableRegister(instruction);
            case 0x7 -> decodeAddToVariableRegister(instruction);
            case 0x8 -> decodeArithmeticInstruction(instruction);
            case 0x9 -> decodeSkipIfVariableRegistersNotEqual(instruction);
            case 0xA -> decodeSetIndexRegister(instruction);
            case 0xB -> decodeJumpWithOffset(instruction);
            case 0xC -> decodeMaskedRandom(instruction);
            case 0xD -> decodeDrawSprite(instruction);
            case 0xE -> decodeSkipInstruction(instruction);
            case 0xF -> decodeMiscellaneousInstruction(instruction);
            default -> NOOP;
        };
    }

    private Runnable decodeMiscellaneousInstruction(int instruction) {
        int xRegister = decodeXRegister(instruction);

        return switch (instruction & 0x00FF) {
            case 0x07 -> new SetVariableRegisterFromDelayTimer(variableRegisters, delayTimer, xRegister);
            case 0x0A -> new WaitForKeyPress(keyboard, variableRegisters, xRegister);
            case 0x15 -> new SetDelayTimerFromVariableRegister(variableRegisters, delayTimer, xRegister);
            case 0x1E -> new AddVariableRegisterToIndexRegister(variableRegisters, indexRegister, xRegister);
            case 0x29 -> new SetIndexRegisterToFontCharacterLocation(variableRegisters, indexRegister, xRegister);
            case 0x33 -> new StoreVariableRegisterToMemory(variableRegisters, indexRegister, memory, xRegister);
            case 0x55 -> new StoreVariableRegistersToMemory(variableRegisters, indexRegister, memory, xRegister);
            case 0x65 -> new LoadVariableRegistersFromMemory(variableRegisters, indexRegister, memory, xRegister);
            default -> NOOP;
        };
    }

    private Runnable decodeSkipInstruction(int instruction) {
        int xRegister = decodeXRegister(instruction);

        return switch (instruction & 0x00FF) {
            case 0x9E -> new SkipIfKeyPressed(programCounter, variableRegisters, keyboard, xRegister);
            case 0xA1 -> new SkipIfKeyNotPressed(programCounter, variableRegisters, keyboard, xRegister);
            default -> NOOP;
        };
    }

    private DrawSprite decodeDrawSprite(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int yRegister = decodeYRegister(instruction);
        int height = decodeHeight(instruction);

        return new DrawSprite(memory, indexRegister, variableRegisters, displayModel, xRegister, yRegister, height);
    }

    private MaskedRandom decodeMaskedRandom(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int constant = decodeConstant(instruction);

        return new MaskedRandom(variableRegisters, random, xRegister, constant);
    }

    private JumpWithOffset decodeJumpWithOffset(int instruction) {
        int location = decodeLocation(instruction);

        return new JumpWithOffset(programCounter, variableRegisters, location);
    }

    private SetIndexRegister decodeSetIndexRegister(int instruction) {
        int location = decodeLocation(instruction);

        return new SetIndexRegister(indexRegister, location);
    }

    private SkipIfVariableRegistersNotEqual decodeSkipIfVariableRegistersNotEqual(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int yRegister = decodeYRegister(instruction);

        return new SkipIfVariableRegistersNotEqual(variableRegisters, programCounter, xRegister, yRegister);
    }

    private Runnable decodeArithmeticInstruction(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int yRegister = decodeYRegister(instruction);

        return switch (instruction & 0x000F) {
            case 0x0 -> new CopyVariableRegister(variableRegisters, xRegister, yRegister);
            case 0x1 -> new OrVariableRegisters(variableRegisters, xRegister, yRegister);
            case 0x2 -> new AndVariableRegisters(variableRegisters, xRegister, yRegister);
            case 0x3 -> new XorVariableRegisters(variableRegisters, xRegister, yRegister);
            case 0x4 -> new AddVariableRegisters(variableRegisters, xRegister, yRegister);
            case 0x5 -> new SubtractVariableRegisters(variableRegisters, xRegister, yRegister);
            case 0x6 -> new ShiftRightVariableRegisters(variableRegisters, xRegister, yRegister);
            case 0x7 -> new NegativeSubtractVariableRegisters(variableRegisters, xRegister, yRegister);
            case 0xE -> new ShiftLeftVariableRegisters(variableRegisters, xRegister, yRegister);
            default -> NOOP;
        };
    }

    private AddToVariableRegister decodeAddToVariableRegister(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int constant = decodeConstant(instruction);

        return new AddToVariableRegister(variableRegisters, xRegister, constant);
    }

    private SetVariableRegister decodeSetVariableRegister(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int constant = decodeConstant(instruction);

        return new SetVariableRegister(variableRegisters, xRegister, constant);
    }

    private SkipIfVariableRegistersEqual decodeSkipIfVariableRegistersEqual(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int yRegister = decodeYRegister(instruction);

        return new SkipIfVariableRegistersEqual(variableRegisters, programCounter, xRegister, yRegister);
    }

    private SkipIfNotEqualToVariableRegister decodeSkipIfNotEqualToVariableRegister(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int constant = decodeConstant(instruction);

        return new SkipIfNotEqualToVariableRegister(variableRegisters, programCounter, xRegister, constant);
    }

    private SkipIfEqualToVariableRegister decodeSkipIfEqualToVariableRegister(int instruction) {
        int xRegister = decodeXRegister(instruction);
        int constant = decodeConstant(instruction);

        return new SkipIfEqualToVariableRegister(variableRegisters, programCounter, xRegister, constant);
    }

    private Call decodeCall(int instruction) {
        int location = decodeLocation(instruction);

        return new Call(programCounter, stack, location);
    }

    private Jump decodeJump(int instruction) {
        int location = decodeLocation(instruction);

        return new Jump(programCounter, location);
    }

    private static int decodeXRegister(int instruction) {
        return (instruction & 0x0F00) >> 8;
    }

    private static int decodeYRegister(int instruction) {
        return (instruction & 0x00F0) >> 4;
    }

    private static int decodeLocation(int instruction) {
        return instruction & 0x0FFF;
    }

    private static int decodeConstant(int instruction) {
        return instruction & 0x00FF;
    }

    private static int decodeHeight(int instruction) {
        return instruction & 0x000F;
    }
}
