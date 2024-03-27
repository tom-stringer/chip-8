package chip8.ui;

import chip8.input.Keyboard;

import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

// TODO: package private
public class AwtKeyboardAdapter {
    private final Keyboard keyboard;

    private final Map<Integer, Integer> awtKeyCodes;

    public AwtKeyboardAdapter(Keyboard keyboard, int[] awtKeyCodes) {
        if (awtKeyCodes.length != 16)
        {
            throw new IllegalArgumentException("awtKeyCodes must have a length of 16");
        }

        this.keyboard = keyboard;
        this.awtKeyCodes = IntStream
                .range(0, awtKeyCodes.length)
                .mapToObj(key -> Map.entry(awtKeyCodes[key], key))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public void pressKey(int keyCode) {
        Optional.ofNullable(awtKeyCodes.get(keyCode))
                .ifPresent(keyboard::pressKey);
    }

    public void releaseKey(int keyCode) {
        Optional.ofNullable(awtKeyCodes.get(keyCode))
                .ifPresent(keyboard::releaseKey);
    }
}
