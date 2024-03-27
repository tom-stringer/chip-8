package chip8.input;

import chip8.ui.AwtKeyboardAdapter;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.OptionalInt;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.awt.event.KeyEvent.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class AwtKeyboardAdapterTest {

    private static final int[] AWT_KEY_CODES = new int[] {
            VK_1,
            VK_2,
            VK_3,
            VK_4,
            VK_5,
            VK_6,
            VK_7,
            VK_8,
            VK_Q,
            VK_W,
            VK_E,
            VK_R,
            VK_T,
            VK_Y,
            VK_U,
            VK_I,
    };

    @ParameterizedTest
    @ValueSource(ints = { 0, 1, 5, 15, 17, 20 })
    void givenAwtKeyCodesOfIncorrectLength_whenConstructKeyboardAdpater_thenExceptionThrown(int size)
    {
        Keyboard keyboard = new Keyboard();

        assertThatExceptionOfType(IllegalArgumentException.class).isThrownBy(() -> new AwtKeyboardAdapter(keyboard, new int[size]));
    }

    @ParameterizedTest
    @MethodSource("awtKeyCodeAndExpectedKey")
    void givenKeyboardAdapter_whenPressKey_thenCorrectKeyPressed(int awtKeyCode, int expectedKey)
    {
        Keyboard keyboard = new Keyboard();
        AwtKeyboardAdapter adapter = new AwtKeyboardAdapter(keyboard, AWT_KEY_CODES);

        adapter.pressKey(awtKeyCode);

        OptionalInt key = keyboard.getPressedKey();
        assertThat(key).hasValue(expectedKey);
    }

    private static Stream<Arguments> awtKeyCodeAndExpectedKey() {
        return IntStream.range(0, AWT_KEY_CODES.length).mapToObj(index -> arguments(AWT_KEY_CODES[index], index));
    }

    @ParameterizedTest
    @ValueSource(ints = { VK_9, VK_O, VK_SHIFT })
    void givenKeyboardAdapter_whenPressUnknownKey_thenNoKeyPressed(int awtKeyCode)
    {
        Keyboard keyboard = new Keyboard();
        AwtKeyboardAdapter adapter = new AwtKeyboardAdapter(keyboard, AWT_KEY_CODES);

        adapter.pressKey(awtKeyCode);

        OptionalInt key = keyboard.getPressedKey();
        assertThat(key).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("awtKeyCode")
    void givenKeyboardAdapter_andKeyPressed_whenReleaseKey_thenNoKeyPressed(int awtKeyCode)
    {
        Keyboard keyboard = new Keyboard();
        AwtKeyboardAdapter adapter = new AwtKeyboardAdapter(keyboard, AWT_KEY_CODES);
        adapter.pressKey(awtKeyCode);

        adapter.releaseKey(awtKeyCode);

        OptionalInt key = keyboard.getPressedKey();
        assertThat(key).isEmpty();
    }

    private static Stream<Arguments> awtKeyCode() {
        return Arrays.stream(AWT_KEY_CODES).mapToObj(Arguments::arguments);
    }

    @ParameterizedTest
    @ValueSource(ints = { VK_9, VK_O, VK_SHIFT })
    void givenKeyboardAdapter_andKeyPressed_whenReleaseUnknownKey_thenKeyStillPressed(int awtKeyCode)
    {
        Keyboard keyboard = new Keyboard();
        AwtKeyboardAdapter adapter = new AwtKeyboardAdapter(keyboard, AWT_KEY_CODES);
        adapter.pressKey(AWT_KEY_CODES[0]);

        adapter.releaseKey(awtKeyCode);

        OptionalInt key = keyboard.getPressedKey();
        assertThat(key).hasValue(0);
    }
}