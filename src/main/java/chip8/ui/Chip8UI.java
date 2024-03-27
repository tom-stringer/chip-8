package chip8.ui;

import chip8.Chip8Configuration;

import javax.swing.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static java.awt.event.KeyEvent.*;
import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class Chip8UI {
    private static final int WIDTH = 64 * Display.PIXEL_SIZE;
    private static final int HEIGHT = 32 * Display.PIXEL_SIZE + 20; // TODO: sort this one day
    private static final int[] KEYBOARD_KEYS = new int[] {
            VK_X,
            VK_1,
            VK_2,
            VK_3,
            VK_Q,
            VK_W,
            VK_E,
            VK_A,
            VK_S,
            VK_D,
            VK_Z,
            VK_C,
            VK_4,
            VK_R,
            VK_F,
            VK_V
    };

    private Chip8UI() {}

    public static void initialize(Chip8Configuration<Display> chip8Configuration) {
        JFrame jFrame = new JFrame();
        jFrame.add(chip8Configuration.display());
        jFrame.setSize(WIDTH, HEIGHT);
        jFrame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        jFrame.setVisible(true);

        AwtKeyboardAdapter keyboardAdapter = new AwtKeyboardAdapter(chip8Configuration.keyboard(), KEYBOARD_KEYS);

        jFrame.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                keyboardAdapter.pressKey(e.getKeyCode());
            }

            @Override
            public void keyReleased(KeyEvent e) {
                keyboardAdapter.releaseKey(e.getKeyCode());
            }
        });
    }
}
