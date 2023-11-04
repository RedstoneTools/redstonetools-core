package tools.redstone.config.options;

import tools.redstone.config.IOption;
import tools.redstone.config.Option;
import tools.redstone.core.shared.serialization.ISerializer;
import tools.redstone.core.shared.serialization.serializers.IntSerializer;

public class IntOption implements IOption<Integer> {
    private final IntSerializer serializer;

    @Override
    public ISerializer<Integer> getSerializer() {
        return serializer;
    }

    public IntOption(Integer min, Integer max) {
        this.serializer = new IntSerializer(min, max);
    }

    public IntOption(Integer max) {
        this.serializer = new IntSerializer(Integer.MIN_VALUE, max);
    }

    public IntOption() {
        this.serializer = new IntSerializer(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }

    @Override
    public void render(Option<Integer> option) {
        var min = serializer.getMin();
        var max = serializer.getMax();

        var defaultVal = option.getDefault();
        var currentVal = option.getValue();

        System.out.println("min = " + min);
        System.out.println("max = " + max);
        System.out.println("defaultVal = " + defaultVal);
        System.out.println("currentVal = " + currentVal);
    }

}
