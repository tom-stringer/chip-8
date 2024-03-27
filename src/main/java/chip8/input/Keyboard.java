package chip8.input;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;

import static java.util.stream.Collectors.toMap;

public class Keyboard {

    private static final List<Integer> KEYS = List.of(
            0x1, 0x2, 0x3, 0xC,
            0x4, 0x5, 0x6, 0xD,
            0x7, 0x8, 0x9, 0xE,
            0xA, 0x0, 0xB, 0xF
    );

    private final Map<Integer, AtomicBoolean> pressedKeys;
    private final List<Listener> listeners;

    public Keyboard() {
        pressedKeys = KEYS
                .stream()
                .collect(toMap(Function.identity(), key -> new AtomicBoolean(), (key1, key2) -> key1, LinkedHashMap::new));
        listeners = new CopyOnWriteArrayList<>();
    }

    public void pressKey(int key) {
        if (key >= 0 && key <= 0xF)
        {
            pressedKeys.get(key).set(true);

            listeners.forEach(listener -> listener.keyPressed(key));
        }
    }

    public void releaseKey(int key) {
        if (key >= 0 && key <= 0xF)
        {
            pressedKeys.get(key).set(false);

            listeners.forEach(listener -> listener.keyReleased(key));
        }
    }

    public OptionalInt getPressedKey() {
        return pressedKeys
                .entrySet()
                .stream()
                .filter(entry -> entry.getValue().get())
                .mapToInt(Map.Entry::getKey)
                .findFirst();
    }

    public int waitForKeyPress() {
        Listener listener = new Listener();
        listeners.add(listener);
        return listener.key();
    }

    private class Listener
    {
        private final CountDownLatch latch = new CountDownLatch(2);
        private int key = -1;

        private void keyPressed(int key)
        {
            if (this.key == -1) {
                this.key = key;
                latch.countDown();
            }
        }

        private void keyReleased(int key)
        {
            if (this.key == key) {
                listeners.remove(this);
                latch.countDown();
            }
        }

        private int key()
        {
            try
            {
                latch.await();
            }
            catch (InterruptedException e)
            {
                Thread.currentThread().interrupt();
                throw new RuntimeException(e);
            }
            finally
            {
                listeners.remove(this);
            }
            return key;
        }
    }
}
