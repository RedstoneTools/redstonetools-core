package tools.redstone.core.shared.serialization;

import java.util.Optional;
import java.util.function.Predicate;

public interface IStringReader {
    default Optional<Character> peek() {
        return peek(1)
                .map(s -> s.charAt(0));
    }

    default Optional<Character> read() {
        return read(1)
                .map(s -> s.charAt(0));
    }

    default boolean startsWith(String string) {
        return peek(string.length())
                .map(s -> s.equals(string))
                .orElse(false);
    }

    default boolean skipIfStartsWith(String string) {
        var startsWithString = startsWith(string);

        if (startsWithString) {
            skip(string.length());
        }

        return startsWithString;
    }

    default String peekWhile(Predicate<Character> predicate) {
        var stringBuilder = new StringBuilder();
        var initialCursor = getCursor();
        var peekedChar = peek();

        while (peekedChar.isPresent() && predicate.test(peekedChar.get())) {
            stringBuilder.append(peekedChar.get());
            skip(1);
            peekedChar = peek();
        }

        setCursor(initialCursor);
        return stringBuilder.toString();
    }

    default String readWhile(Predicate<Character> predicate) {
        var readCharacters = peekWhile(predicate);
        skip(readCharacters.length());
        return readCharacters;
    }

    default String readWord() {
        return readWhile(c -> c != ' ');
    }

    default Optional<String> read(int amount) {
        Optional<String> result = peek(amount);

        if (result.isPresent()) {
            setCursor(getCursor() + amount);
        }

        return result;
    }

    default int skip(int amount) {
        var originalCursor = getCursor();
        var newCursor = setCursor(originalCursor + amount);
        return newCursor - originalCursor;
    }

    int getCursor();

    int setCursor(int position);

    Optional<String> peek(int amount);
}
