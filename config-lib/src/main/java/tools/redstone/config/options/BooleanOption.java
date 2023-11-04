package tools.redstone.config.options;

import tools.redstone.config.IOption;
import tools.redstone.config.Option;
import tools.redstone.core.shared.serialization.ISerializer;
import tools.redstone.core.shared.serialization.serializers.BooleanSerializer;

public class BooleanOption implements IOption<Boolean> {
    private final BooleanSerializer serializer;

    @Override
    public ISerializer<Boolean> getSerializer() {
        return serializer;
    }

    public BooleanOption() {
        this.serializer = new BooleanSerializer();
    }

    @Override
    public void render(Option<Boolean> option) {
        throw new UnsupportedOperationException("Unimplemented method 'render'");
    }

}
