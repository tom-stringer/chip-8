package chip8.instruction;

import chip8.IndexRegister;
import chip8.Memory;
import chip8.VariableRegisters;
import chip8.display.DisplayModel;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DrawSpriteTest
{
    @Test
    void givenDisplayModel_whenDraw8x8AllPixelsOnSprite_thenSpriteIsDrawn()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();

        memory.loadProgram(new byte[] {(byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        indexRegister.set(0x200);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 0, 0, 8).run();

        boolean[][] expected = new boolean[64][32];

        for (int i = 0; i < 8; i++)
        {
            for (int j = 0; j < 8; j++)
            {
                expected[i][j] = true;
            }
        }

        assertThat(displayModel.pixels()).isEqualTo(expected);
    }

    @Test
    void givenDisplayModel_whenDraw8x8FirstRowOffSprite_thenSpriteIsDrawn()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();

        memory.loadProgram(new byte[] {(byte) 0x00, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF, (byte) 0xFF});
        indexRegister.set(0x200);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 0, 0, 8).run();

        boolean[][] expected = new boolean[64][32];

        for (int y = 1; y < 8; y++)
        {
            for (int x = 0; x < 8; x++)
            {
                expected[x][y] = true;
            }
        }

        assertThat(displayModel.pixels()).isEqualTo(expected);
    }

    @Test
    void givenDisplayModel_whenDraw8x8FirstColumnOffSprite_thenSpriteIsDrawn()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();

        memory.loadProgram(new byte[] {(byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F});
        indexRegister.set(0x200);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 0, 0, 8).run();

        boolean[][] expected = new boolean[64][32];

        for (int y = 0; y < 8; y++)
        {
            for (int x = 1; x < 8; x++)
            {
                expected[x][y] = true;
            }
        }

        assertThat(displayModel.pixels()).isEqualTo(expected);
    }

    @Test
    void givenDisplayModel_whenDrawSpriteBeyondHorizontalAxis_thenSpriteIsWrappedHorizontally()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();

        variableRegisters.get(0).set(63);
        memory.loadProgram(new byte[] {(byte) 0x7F});
        indexRegister.set(0x200);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 0, 1, 8).run();

        boolean[][] expected = new boolean[64][32];

        for (int x = 0; x < 7; x++)
        {
            expected[x][0] = true;
        }

        assertThat(displayModel.pixels()).isEqualTo(expected);
    }

    @Test
    void givenDisplayModel_whenDrawSpriteBeyondVerticalAxis_thenSpriteIsWrappedVertically()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();

        variableRegisters.get(1).set(31);
        memory.loadProgram(new byte[] {(byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F, (byte) 0x7F});
        indexRegister.set(0x200);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 0, 1, 8).run();

        boolean[][] expected = new boolean[64][32];

        for (int y = 0; y < 7; y++)
        {
            for (int x = 1; x < 8; x++)
            {
                expected[x][y] = true;
            }
        }

        for (int x = 1; x < 8; x++)
        {
            expected[x][31] = true;
        }

        assertThat(displayModel.pixels()).isEqualTo(expected);
    }

    @Test
    void givenVFIsSet_andDisplayModelWithPixelOff_whenDrawPixel_thenVFIsNotSet()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();
        variableRegisters.get(0xF).set(0x1);

        indexRegister.set(0x200);
        displayModel.togglePixel(0, 0);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 0, 0, 1).run();

        assertThat(variableRegisters.get(0xF).get()).isZero();
    }

    @Test
    void givenDisplayModelWithPixelOn_whenDrawPixel_thenVFIsSet()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();

        memory.loadProgram(new byte[] {(byte) 0x80});
        indexRegister.set(0x200);
        displayModel.togglePixel(0, 0);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 0, 0, 1).run();

        assertThat(variableRegisters.get(0xF).get()).isOne();
    }

    @Test
    void givenDisplayModel_andVariableRegistersSet_whenDrawPixel_thenPixelIsOn()
    {
        Memory memory = new Memory();
        IndexRegister indexRegister = new IndexRegister();
        VariableRegisters variableRegisters = new VariableRegisters();
        DisplayModel displayModel = new DisplayModel();

        memory.loadProgram(new byte[] {(byte) 0x80});
        indexRegister.set(0x200);
        variableRegisters.get(1).set(10);
        variableRegisters.get(2).set(20);

        new DrawSprite(memory, indexRegister, variableRegisters, displayModel, 1, 2, 1).run();

        assertThat(displayModel.pixel(10, 20)).isTrue();
    }
}