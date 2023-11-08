package tools.redstone.core.shared.serialization.serializers;

import java.util.ArrayList;
import java.util.List;

import tools.redstone.core.shared.serialization.ISerializer;
import tools.redstone.core.shared.serialization.IStringReader;
import tools.redstone.core.shared.serialization.SuggestionContext;

public class StringSerializer implements ISerializer<String> {
    @Override
    public String serialize(String value) {
        return "\"" + value
                .replace("\\\"", "\"")
                .replace("\\f", "\f")
                .replace("\\r", "\r")
                .replace("\\n", "\n")
                .replace("\\b", "\b")
                .replace("\\t", "\t")
                .replace("\\\\", "\\")
                + "\"";
    }

    @Override
    public String deserialize(IStringReader stringReader) {
        if (stringReader.peek().orElseThrow() == '\"') {
            return readQuotedString(stringReader);
        } else {
            return readUnQuotedString(stringReader);
        }

    }

    @Override
    public List<String> getSuggestions(SuggestionContext context, String string) {
        return new ArrayList<>();
    }

    private String readQuotedString(IStringReader stringReader) {
        stringReader.skip(1); // Skips the opening quote
        var stringBuilder = new StringBuilder();

        while (true) {
            var character = stringReader.read().orElseThrow(() -> new RuntimeException("Unexpected end of input"));

            if (character == '\\') {
                stringBuilder.append(stringReader.read().orElseThrow());
            } else if (character == '\"') {
                return stringBuilder.toString();
            } else {
                stringBuilder.append(character);
            }
        }
    }

    private String readUnQuotedString(IStringReader stringReader) {
        return stringReader.readWord();
    }
}
