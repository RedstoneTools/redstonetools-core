package tools.redstone.core.shared.serialization.serializers;

import java.util.ArrayList;
import java.util.List;

import tools.redstone.core.shared.serialization.ISerializer;
import tools.redstone.core.shared.serialization.SuggestionContext;

public class BooleanSerializer implements ISerializer<Boolean> {
    public BooleanSerializer() {
    }

    @Override
    public String serialize(Boolean value) {
        return value.toString();
    }

    @Override
    public Boolean deserialize(String string) {
        if (string.equals("true")) {
            return true;
        } else if (string.equals("false")) {
            return false;
        }

        throw new RuntimeException("Invalid boolean '" + string + "'");
    }

    @Override
    public List<String> getSuggestions(SuggestionContext context, String string) {
        final var completions = new ArrayList<String>();
        completions.add("false");
        completions.add("true");
        return completions;
    }
}
