package chip8.display;

import java.util.Arrays;

public class DisplayModel
{
    public static final int WIDTH = 64;

    public static final int HEIGHT = 32;

    private final boolean[][] pixels;

    public DisplayModel()
    {
        pixels = new boolean[WIDTH][HEIGHT];
    }

    public boolean[][] pixels()
    {
        return pixels;
    }

    public boolean pixel(int x, int y)
    {
        return pixels[x][y];
    }

    public void togglePixel(int x, int y)
    {
        pixels[x][y] = !pixels[x][y];
    }

    public void clear()
    {
        Arrays.stream(pixels).forEach(row -> Arrays.fill(row, false));
    }
}
