
package tools.redstone.core.shared.serialization.serializers;

import java.util.ArrayList;
import java.util.List;

import tools.redstone.core.shared.serialization.ISerializer;
import tools.redstone.core.shared.serialization.SuggestionContext;

public class IntSerializer implements ISerializer<Integer> {
    private Integer min;
    private Integer max;

    public IntSerializer(Integer min, Integer max) {
        this.min = min;
        this.max = max;
    }

    @Override
    public String serialize(Integer value) {
        checkBounds(value);
        return value.toString();
    }

    @Override
    public Integer deserialize(String string) {
        var value = Integer.parseInt(string);
        checkBounds(value);
        return value;
    }

    @Override
    public List<String> getSuggestions(SuggestionContext context, String string) {
        return new ArrayList<>();
    }

    public Integer getMin() {
        return min;
    }

    public Integer getMax() {
        return max;
    }

    private void checkBounds(Integer value) {
        if (value < min || value > max) {
            throw new RuntimeException("Integer " + value + " is out of the bounds min: " + min + " max: " + max);
        }
    }

}
