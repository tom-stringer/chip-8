package chip8;

import chip8.display.DisplayView;
import chip8.ui.Display;
import chip8.display.DisplayModel;
import chip8.display.DisplayPresenter;
import chip8.input.Keyboard;
import chip8.timer.DelayTimer;

import java.util.Random;
import java.util.concurrent.Executors;
import java.util.function.Supplier;

import static java.util.Objects.requireNonNullElseGet;

public class Chip8Configuration<T extends DisplayView>
{
    private final int instructionPerSecond;
    private final Supplier<T> displayViewSupplier;

    private Memory memory;
    private ProgramCounter programCounter;
    private Stack stack;
    private IndexRegister indexRegister;
    private VariableRegisters variableRegisters;
    private DisplayModel displayModel;
    private T displayView;
    private DisplayPresenter displayPresenter;
    private Keyboard keyboard;
    private DelayTimer delayTimer;
    private Random random;
    private FetchDecodeExecutor fetchDecodeExecutor;
    private Emulator emulator;

    public Chip8Configuration(int instructionPerSecond, Supplier<T> displayViewSupplier) {
        this.instructionPerSecond = instructionPerSecond;
        this.displayViewSupplier = displayViewSupplier;
    }

    public Memory memory()
    {
        return requireNonNullElseGet(
                memory,
                () -> memory = new Memory()
        );
    }

    public ProgramCounter programCounter()
    {
        return requireNonNullElseGet(
                programCounter,
                () -> programCounter = new ProgramCounter()
        );
    }

    public Stack stack()
    {
        return requireNonNullElseGet(
                stack,
                () -> stack = new Stack(programCounter())
        );
    }

    public IndexRegister indexRegister()
    {
        return requireNonNullElseGet(
                indexRegister,
                () -> indexRegister = new IndexRegister()
        );
    }

    public VariableRegisters variableRegisters()
    {
        return requireNonNullElseGet(
                variableRegisters,
                () -> variableRegisters = new VariableRegisters()
        );
    }

    public DisplayModel displayModel()
    {
        return requireNonNullElseGet(
                displayModel,
                () -> displayModel = new DisplayModel()
        );
    }

    public T display() {
        return requireNonNullElseGet(
                displayView,
                () -> displayView = displayViewSupplier.get()
        );
    }

    public DisplayPresenter displayPresenter() {
        return requireNonNullElseGet(
                displayPresenter,
                () -> displayPresenter = new DisplayPresenter(displayModel(), display())
        );
    }

    public Keyboard keyboard() {
        return requireNonNullElseGet(
                keyboard,
                () -> keyboard = new Keyboard()
        );
    }

    public DelayTimer delayTimer()
    {
        return requireNonNullElseGet(
                delayTimer,
                () -> delayTimer = new DelayTimer(Executors.newSingleThreadExecutor())
        );
    }

    public Random random()
    {
        return requireNonNullElseGet(
                random,
                () -> random = new Random()
        );
    }

    public FetchDecodeExecutor fetchDecodeExecutor()
    {
        return requireNonNullElseGet(
                fetchDecodeExecutor,
                () -> fetchDecodeExecutor = new FetchDecodeExecutor(memory(), programCounter(), stack(), displayModel(), indexRegister(), variableRegisters(), keyboard(), delayTimer(), random())
        );
    }

    public Emulator emulator()
    {
        return requireNonNullElseGet(
                emulator,
                () -> emulator = new Emulator(displayPresenter(), memory(), fetchDecodeExecutor(), instructionPerSecond)
        );
    }
}
