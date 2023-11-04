package tools.redstone.config;

import tools.redstone.core.shared.serialization.ISerializer;

public interface IOption<T> {
    void render(Option<T> option);

    ISerializer<T> getSerializer();
}
