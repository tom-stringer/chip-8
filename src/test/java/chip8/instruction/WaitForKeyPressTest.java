package chip8.instruction;

import chip8.VariableRegisters;
import chip8.input.Keyboard;
import org.junit.jupiter.api.Test;

import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.assertj.core.api.Assertions.assertThat;

class WaitForKeyPressTest
{
    @Test
    void givenNoKeyPressed_andWaitForKeyPress_whenKeyPressed_andKeyReleased_thenGivenRegisterUpdatedToCorrectValue() throws Exception {
        VariableRegisters variableRegisters = new VariableRegisters();
        Keyboard keyboard = new Keyboard();

        Future<?> futureKey = Executors.newSingleThreadExecutor().submit(new WaitForKeyPress(keyboard, variableRegisters, 0x2));
        Thread.sleep(100);
        keyboard.pressKey(0xA);
        keyboard.releaseKey(0xA);

        futureKey.get(1, SECONDS);
        assertThat(variableRegisters.get(0x2).get()).isEqualTo(0xA);
    }
}