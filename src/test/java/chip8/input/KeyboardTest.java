package chip8.input;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.OptionalInt;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

class KeyboardTest
{
    @Test
    void givenNoKeysPressed_whenGetPressedKey_thenEmpty()
    {
        Keyboard keyboard = new Keyboard();

        OptionalInt key = keyboard.getPressedKey();

        assertThat(key).isEmpty();
    }

    @ParameterizedTest
    @ValueSource(ints = { 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF })
    void givenKeyPressed_whenGetPressedKey_thenCorrectKeyPresent(int keyPressed)
    {
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(keyPressed);

        OptionalInt key = keyboard.getPressedKey();

        assertThat(key).hasValue(keyPressed);
    }

    @ParameterizedTest
    @ValueSource(ints = { 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF })
    void givenKeyPressed_andKeyReleased_whenGetPressedKey_thenEmpty(int keyPressed)
    {
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(keyPressed);
        keyboard.releaseKey(keyPressed);

        OptionalInt key = keyboard.getPressedKey();

        assertThat(key).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("pressedKeysAndExpectedKey")
    void givenMultipleKeysPressed_whenGetPressedKey_thenCorrectKeyPresent(int firstKeyPressed, int secondKeyPressed, int expectedKey)
    {
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(firstKeyPressed);
        keyboard.pressKey(secondKeyPressed);

        OptionalInt key = keyboard.getPressedKey();

        assertThat(key).hasValue(expectedKey);
    }

    public static Stream<Arguments> pressedKeysAndExpectedKey() {
        return Stream.of(
                arguments(0x1, 0x2, 0x1),
                arguments(0x1, 0xC, 0x1),
                arguments(0x1, 0xF, 0x1),
                arguments(0x1, 0xA, 0x1),
                arguments(0x4, 0x7, 0x4),
                arguments(0x5, 0x6, 0x5),
                arguments(0x9, 0xE, 0x9),
                arguments(0x9, 0xB, 0x9),
                arguments(0xA, 0xF, 0xA),
                arguments(0xC, 0xF, 0xC)
        );
    }

    @ParameterizedTest
    @MethodSource("pressedKeys")
    void givenMultipleKeysPressed_andFirstKeyReleased_whenGetPressedKey_thenSecondKeyPresent(int firstKeyPressed, int secondKeyPressed)
    {
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(firstKeyPressed);
        keyboard.pressKey(secondKeyPressed);
        keyboard.releaseKey(firstKeyPressed);

        OptionalInt key = keyboard.getPressedKey();

        assertThat(key).hasValue(secondKeyPressed);
    }

    @ParameterizedTest
    @MethodSource("pressedKeys")
    void givenMultipleKeysPressed_andSecondKeyReleased_whenGetPressedKey_thenFirstKeyPresent(int firstKeyPressed, int secondKeyPressed)
    {
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(firstKeyPressed);
        keyboard.pressKey(secondKeyPressed);
        keyboard.releaseKey(secondKeyPressed);

        OptionalInt key = keyboard.getPressedKey();

        assertThat(key).hasValue(firstKeyPressed);
    }

    public static Stream<Arguments> pressedKeys() {
        return Stream.of(
                arguments(0x1, 0x2),
                arguments(0x1, 0xC),
                arguments(0x1, 0xF),
                arguments(0x1, 0xA),
                arguments(0x4, 0x7),
                arguments(0x5, 0x6),
                arguments(0x9, 0xE),
                arguments(0x9, 0xB),
                arguments(0xA, 0xF),
                arguments(0xC, 0xF)
        );
    }

    @ParameterizedTest
    @ValueSource(ints = { 0x0, 0x1, 0x2, 0x3, 0x4, 0x5, 0x6, 0x7, 0x8, 0x9, 0xA, 0xB, 0xC, 0xD, 0xE, 0xF })
    void givenKeyNotPressed_andWaitingForKeyPress_whenKeyPressed_andKeyReleased_thenCorrectKeyReturned(int key) throws Exception
    {
        Keyboard keyboard = new Keyboard();
        Future<Integer> futureKey = Executors.newSingleThreadExecutor().submit(keyboard::waitForKeyPress);
        Thread.sleep(100);

        keyboard.pressKey(key);
        keyboard.releaseKey(key);

        assertThat(futureKey.get(1, SECONDS)).isEqualTo(key);
    }

    @Test
    void givenKeyPressed_andWaitForKeyPress_whenOtherKeyPressed_andReleased_thenCorrectKeyReturned() throws Exception
    {
        Keyboard keyboard = new Keyboard();
        keyboard.pressKey(0x2);
        Future<Integer> futureKey = Executors.newSingleThreadExecutor().submit(keyboard::waitForKeyPress);
        Thread.sleep(100);

        keyboard.releaseKey(0x2);
        keyboard.pressKey(0x1);
        keyboard.releaseKey(0x1);

        assertThat(futureKey.get(1, SECONDS)).isEqualTo(0x1);
    }

    @Test
    void givenNoKeysPressed_andWaitForKeyPress_whenTwoKeysPressed_andOneReleased_thenCorrectKeyReturned() throws Exception
    {
        Keyboard keyboard = new Keyboard();
        Future<Integer> futureKey = Executors.newSingleThreadExecutor().submit(keyboard::waitForKeyPress);
        Thread.sleep(100);

        keyboard.pressKey(0x2);
        keyboard.pressKey(0x1);
        keyboard.releaseKey(0x2);

        assertThat(futureKey.get(1, SECONDS)).isEqualTo(0x2);
    }
}