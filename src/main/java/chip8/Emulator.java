package chip8;

import chip8.display.DisplayPresenter;

public class Emulator implements Runnable
{
    private static final int ONE_SECOND_IN_NANOS = 1_000_000_000;

    private final DisplayPresenter displayPresenter;
    private final FetchDecodeExecutor fetchDecodeExecutor;
    private final int instructionExecutionPeriod;
    private final Memory memory;

    private volatile boolean isRunning;

    public Emulator(DisplayPresenter displayPresenter, Memory memory, FetchDecodeExecutor fetchDecodeExecutor, int instructionPerSecond)
    {
        this.displayPresenter = displayPresenter;
        this.memory = memory;
        this.fetchDecodeExecutor = fetchDecodeExecutor;
        this.instructionExecutionPeriod = ONE_SECOND_IN_NANOS / instructionPerSecond;
    }

    public void loadProgram(byte[] program)
    {
        memory.loadProgram(program);
    }

    @Override
    public void run()
    {
        isRunning = true;

        while (isRunning)
        {
            long resumeTime = System.nanoTime() + instructionExecutionPeriod;

            fetchDecodeExecutor.fetchDecodeExecute();

            displayPresenter.refresh();

            while (isRunning && System.nanoTime() < resumeTime)
            {
                Thread.onSpinWait();
            }
        }
    }

    public void stop()
    {
        isRunning = false;
    }
}
