package tools.redstone.config.format;

import tools.redstone.core.shared.serialization.ISerializer;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.regex.Pattern;
import java.util.stream.Stream;

public class ConfigSection implements IConfigSection {
    private final Map<String, IConfigSection> sections;
    private final Map<String, String> values;

    private ConfigSection(Map<String, IConfigSection> sections, Map<String, String> values) {
        this.sections = sections;
        this.values = values;
    }

    @Override
    public Stream<String> getSectionNames() {
        return this.sections.keySet().stream();
    }

    @Override
    public Stream<String> getValueNames() {
        return this.values.keySet().stream();
    }

    @Override
    public Optional<IConfigSection> getSection(String name) {
        return getOptionalFromMap(sections, name);
    }

    @Override
    public void setSection(String name, IConfigSection section) {
        sections.put(name, section);
    }

    @Override
    public <T> Optional<T> getValue(String name, ISerializer<T> serializer) {
        return getOptionalFromMap(values, name)
                .map(serializer::deserialize);
    }

    @Override
    public <T> void setValue(String name, T value, ISerializer<T> serializer) {
        setValue(name, serializer.serialize(value));
    }

    private void setValue(String name, String value) {
        values.put(name, value);
    }

    private static <TKey, TValue> Optional<TValue> getOptionalFromMap(Map<TKey, TValue> map, TKey key) {
        if (!map.containsKey(key)) {
            return Optional.empty();
        }

        return Optional.of(map.get(key));
    }

    public static ConfigSection createEmptySection() {
        return new ConfigSection(new HashMap<>(), new HashMap<>());
    }

    public static ConfigSection fromFile(String path) throws IOException {
        return ConfigSection.fromString(Files.readString(Path.of(path)));
    }

    public static ConfigSection fromString(String string) {
        var sectionStartRegex = Pattern.compile("^([A-z_][A-z_0-9]*?)\\h*\\{\\h*\\n");
        var sectionEndRegex = Pattern.compile("^}\\h*\\n");
        var valueRegex = Pattern.compile("^([A-z_][A-z_0-9]*?)\\h*=\\h*(.+?)\\h*\\n");

        string = string + "\n";

        var sectionStack = new Stack<ConfigSection>() {{
            push(ConfigSection.createEmptySection());
        }};

        while (string.stripLeading().length() > 0) {
            string = string.stripLeading();

            var sectionStartMatch = sectionStartRegex.matcher(string);
            if (sectionStartMatch.find()) {
                var subSection = ConfigSection.createEmptySection();
                var name = sectionStartMatch.group(1);

                sectionStack.lastElement().setSection(name, subSection);
                sectionStack.push(subSection);

                string = sectionStartMatch.replaceFirst("");
                continue;
            }

            var sectionEndMatch = sectionEndRegex.matcher(string);
            if (sectionEndMatch.find()) {
                if (sectionStack.size() <= 1) {
                    throw new RuntimeException("Trying to close section when none are open");
                }

                sectionStack.pop();

                string = sectionEndMatch.replaceFirst("");
                continue;
            }

            var valueMatch = valueRegex.matcher(string);
            if (valueMatch.find()) {
                var name = valueMatch.group(1);
                var value = valueMatch.group(2);

                sectionStack.lastElement().setValue(name, value);

                string = valueMatch.replaceFirst("");
                continue;
            }

            throw new RuntimeException("Unexpected line '" + string.lines().findFirst().orElseThrow() + "'");
        }

        if (sectionStack.size() > 1) {
            throw new RuntimeException(sectionStack.size() - 1 + " Unclosed section(s)");
        }

        return sectionStack.pop();
    }
}
