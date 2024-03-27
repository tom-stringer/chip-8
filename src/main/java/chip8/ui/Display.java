package chip8.ui;

import chip8.display.DisplayModel;
import chip8.display.DisplayView;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Display extends JComponent implements DisplayView
{
    public static final int PIXEL_SIZE = 10;

    private final List<Pixel> pixels = new ArrayList<>();

    public Display() {
        for (int i = 0; i < DisplayModel.WIDTH; i++)
        {
            for (int j = 0; j < DisplayModel.HEIGHT; j++)
            {
                pixels.add(new Pixel(i, j));
            }
        }
    }

    @Override
    public void refresh(boolean[][] pixels)
    {
        synchronized (this.pixels)
        {
            int index = 0;

            for (boolean[] row : pixels) {
                for (boolean pixel : row) {
                    this.pixels.get(index++).set(pixel);
                }
            }
        }

        SwingUtilities.invokeLater(this::repaint);
    }

    @Override
    public void paint(Graphics graphics) {
        super.paint(graphics);

        Graphics2D graphics2D = (Graphics2D) graphics.create();

        try
        {
            synchronized (pixels)
            {
                pixels.forEach(pixel -> {
                    graphics2D.setColor(pixel.color());
                    graphics2D.fill(pixel.rectangle());
                });
            }
        }
        finally
        {
            graphics2D.dispose();
        }
    }

    private static final class Pixel
    {
        private static final int FADE_AMOUNT = 8;

        private final Rectangle2D rectangle;
        private Color color;

        public Pixel(int x, int y) {
            this(new Rectangle2D.Float(x * PIXEL_SIZE, y * PIXEL_SIZE, PIXEL_SIZE, PIXEL_SIZE));
        }

        private Pixel(Rectangle2D rectangle) {
            this.rectangle = rectangle;
            this.color = Color.BLACK;
        }

        public Rectangle2D rectangle() {
            return rectangle;
        }

        public Color color() {
            return color;
        }

        public void set(boolean isOn)
        {
            Color fadeColor = new Color(
                    Math.max(0, color.getRed() - FADE_AMOUNT),
                    Math.max(0, color.getGreen() - FADE_AMOUNT),
                    Math.max(0, color.getBlue() - FADE_AMOUNT));

            color = isOn ? Color.WHITE : fadeColor;
        }
    }
}
