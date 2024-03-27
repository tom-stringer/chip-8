package chip8;

import org.assertj.core.api.InstanceOfAssertFactories;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class MemoryTest {

    @Test
    void givenMemory_thenMemoryIsCorrectSize()
    {
        assertThat(new Memory())
                .extracting("bytes", InstanceOfAssertFactories.BYTE_ARRAY)
                .hasSize(4096);
    }

    @Test
    void givenMemory_thenProgramSpaceIsEmpty()
    {
        assertThat(new Memory())
                .extracting("bytes", InstanceOfAssertFactories.BYTE_ARRAY)
                .endsWith(new byte[4096 - 0x200]);
    }

    @Test
    void givenMemory_thenMemoryContainsFont()
    {
        byte[] font = new byte[] {
                (byte) 0xF0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xF0, // 0
                (byte) 0x20, (byte) 0x60, (byte) 0x20, (byte) 0x20, (byte) 0x70, // 1
                (byte) 0xF0, (byte) 0x10, (byte) 0xF0, (byte) 0x80, (byte) 0xF0, // 2
                (byte) 0xF0, (byte) 0x10, (byte) 0xF0, (byte) 0x10, (byte) 0xF0, // 3
                (byte) 0x90, (byte) 0x90, (byte) 0xF0, (byte) 0x10, (byte) 0x10, // 4
                (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x10, (byte) 0xF0, // 5
                (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x90, (byte) 0xF0, // 6
                (byte) 0xF0, (byte) 0x10, (byte) 0x20, (byte) 0x40, (byte) 0x40, // 7
                (byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x90, (byte) 0xF0, // 8
                (byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x10, (byte) 0xF0, // 9
                (byte) 0xF0, (byte) 0x90, (byte) 0xF0, (byte) 0x90, (byte) 0x90, // A
                (byte) 0xE0, (byte) 0x90, (byte) 0xE0, (byte) 0x90, (byte) 0xE0, // B
                (byte) 0xF0, (byte) 0x80, (byte) 0x80, (byte) 0x80, (byte) 0xF0, // C
                (byte) 0xE0, (byte) 0x90, (byte) 0x90, (byte) 0x90, (byte) 0xE0, // D
                (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x80, (byte) 0xF0, // E
                (byte) 0xF0, (byte) 0x80, (byte) 0xF0, (byte) 0x80, (byte) 0x80  // F
        };

        byte[] expectedStart = new byte[0xA0];

        System.arraycopy(font, 0, expectedStart, 0x50, font.length);

        assertThat(new Memory()).extracting("bytes", InstanceOfAssertFactories.BYTE_ARRAY).startsWith(expectedStart);
    }

    @Test
    void givenMemory_whenLoadProgram_thenProgramLoaded()
    {
        Memory memory = new Memory();

        memory.loadProgram(new byte[] { 0x30 });

        assertThat(memory.get(0x200)).isEqualTo((byte) 0x30);
    }

    @Test
    void givenMemoryWithLoadedProgram_whenLoadProgram_thenProgramLoaded()
    {
        Memory memory = new Memory();
        byte[] originalProgram = new byte[4096 - 512];
        Arrays.fill(originalProgram, (byte) 0xFF);
        memory.loadProgram(originalProgram);

        memory.loadProgram(new byte[] { 0x30 });

        assertThat(memory.get(0x200)).isEqualTo((byte) 0x30);
    }

    @Test
    void givenMemory_whenSet_thenMemorySet()
    {
        Memory memory = new Memory();

        memory.set(0x201, (byte) 0x3);

        assertThat(memory.get(0x201)).isEqualTo((byte) 0x3);
    }
}