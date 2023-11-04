package tools.redstone.config.format;

import tools.redstone.core.shared.serialization.ISerializer;

import java.util.Optional;
import java.util.stream.Stream;

public interface IConfigSection {
    Stream<String> getSectionNames();

    Stream<String> getValueNames();

    Optional<IConfigSection> getSection(String name);

    void setSection(String name, IConfigSection section);

    <T> Optional<T> getValue(String name, ISerializer<T> serializer);

    <T> void setValue(String name, T value, ISerializer<T> serializer);
}
