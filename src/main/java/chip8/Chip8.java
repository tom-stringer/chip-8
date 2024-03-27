package chip8;

import chip8.ui.Chip8UI;
import chip8.ui.Display;

import java.io.IOException;
import java.io.InputStream;

public class Chip8 {
    private static final int INSTRUCTION_PER_SECOND = 350;

    public static void main(String[] args) throws IOException {
        Chip8Configuration<Display> chip8Configuration = new Chip8Configuration<>(INSTRUCTION_PER_SECOND, Display::new);

        Chip8UI.initialize(chip8Configuration);

        byte[] bytes = readRom("/online-roms/SQRT Test [Sergey Naydenov, 2010].ch8");

        Emulator emulator = chip8Configuration.emulator();
        emulator.loadProgram(bytes);
        emulator.run();
    }

    private static byte[] readRom(String filename) throws IOException {
        try (InputStream resourceAsStream = Chip8.class.getResourceAsStream(filename))
        {
            return resourceAsStream.readAllBytes();
        }
    }
}
