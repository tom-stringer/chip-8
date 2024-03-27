package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegisters;
import chip8.display.DisplayModel;

public class DrawSprite implements Runnable
{
    private final Memory memory;
    private final IndexRegister indexRegister;
    private final VariableRegisters variableRegisters;
    private final DisplayModel displayModel;
    private final int xRegister;
    private final int yRegister;
    private final int height;

    public DrawSprite(Memory memory, IndexRegister indexRegister, VariableRegisters variableRegisters, DisplayModel displayModel, int xRegister, int yRegister, int height)
    {
        this.memory = memory;
        this.indexRegister = indexRegister;
        this.variableRegisters = variableRegisters;
        this.displayModel = displayModel;
        this.xRegister = xRegister;
        this.yRegister = yRegister;
        this.height = height;
    }

    @Override
    public void run()
    {
        int index = indexRegister.get();
        int x = variableRegisters.get(xRegister).get();
        int y = variableRegisters.get(yRegister).get();
        variableRegisters.get(0xF).set(0x0);

        for (int yOffset = 0; yOffset < height; yOffset++)
        {
            byte spriteRow = memory.get(index + yOffset);

            for (int xOffset = 0; xOffset < 8; xOffset++)
            {
                int bit = ((spriteRow & 0xFF) & (0x80 >> xOffset));

                if (bit != 0)
                {
                    int pixelX = (x + xOffset) % DisplayModel.WIDTH;
                    int pixelY = (y + yOffset) % DisplayModel.HEIGHT;

                    if (displayModel.pixel(pixelX, pixelY))
                    {
                        variableRegisters.get(0xF).set(0x1);
                    }

                    displayModel.togglePixel(pixelX, pixelY);
                }
            }
        }
    }
}
