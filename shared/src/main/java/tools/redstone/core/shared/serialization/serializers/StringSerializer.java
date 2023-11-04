package tools.redstone.core.shared.serialization.serializers;

import java.util.ArrayList;
import java.util.List;

import tools.redstone.core.shared.serialization.ISerializer;
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
    public String deserialize(String string) {
        if (string.charAt(0) != '\"' || string.charAt(string.length() - 1) != '\"') {
            throw new RuntimeException("Missing quotes");
        }

        return string
                .substring(1, string.length() - 1)
                .replace("\\", "\\\\")
                .replace("\t", "\\t")
                .replace("\b", "\\b")
                .replace("\n", "\\n")
                .replace("\r", "\\r")
                .replace("\f", "\\f")
                .replace("\"", "\\\"");
    }

    @Override
    public List<String> getSuggestions(SuggestionContext context, String string) {
        return new ArrayList<>();
    }
}
