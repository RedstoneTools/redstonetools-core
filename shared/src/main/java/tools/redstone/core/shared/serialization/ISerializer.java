package tools.redstone.core.shared.serialization;

import java.util.List;

public interface ISerializer<T> {
    default T deserialize(String input) {
        return deserialize(new StringReader(input));
    }

    String serialize(T value);

    T deserialize(IStringReader stringReader);

    List<String> getSuggestions(SuggestionContext context, String string);
}
