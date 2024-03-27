package chip8.instruction;

import chip8.display.DisplayModel;

public class ClearDisplay implements Runnable
{
    private final DisplayModel displayModel;

    public ClearDisplay(DisplayModel displayModel)
    {
        this.displayModel = displayModel;
    }

    @Override
    public void run()
    {
        displayModel.clear();
    }
}
