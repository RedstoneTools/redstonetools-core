package tools.redstone.core.shared.serialization;

import java.util.Optional;

public class StringReader implements IStringReader {

    private String inputString;
    private int currentIndex;

    public StringReader(String inputString) {
        this.inputString = inputString;
        this.currentIndex = 0;
    }

    @Override
    public Optional<String> peek(int amount) {
        if (currentIndex + amount <= inputString.length()) {
            return Optional.of(inputString.substring(currentIndex, currentIndex + amount));
        } else {
            return Optional.empty();
        }
    }

    @Override
    public int getCursor() {
        return currentIndex;
    }

    @Override
    public int setCursor(int position) {
        var newPosition = Math.min(position, inputString.length());
        currentIndex = newPosition;
        return newPosition;
    }
}
