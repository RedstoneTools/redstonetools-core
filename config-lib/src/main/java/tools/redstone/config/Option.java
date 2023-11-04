package tools.redstone.config;

import tools.redstone.config.format.IConfigSection;
import tools.redstone.core.shared.serialization.ISerializer;

public class Option<T> {
    public static <T> Option<T> ofType(IOption<T> type, T defaultValue) {
        return new Option<>(type, defaultValue);
    }

    private final IOption<T> type;
    private final T defaultValue;
    private String key;
    private String displayName;

    private T value;

    private Option(IOption<T> type, T defaultValue) {
        this.type = type;
        this.defaultValue = defaultValue;
        this.value = defaultValue;
    }

    public Option<T> withKey(String key) {
        this.key = key;
        return this;
    }

    public Option<T> withDisplay(String display) {
        displayName = display;
        return this;
    }

    public void loadValue(IConfigSection config) {
        this.value = config.getValue(this.key, this.getSerializer())
                .orElse(defaultValue);
    }

    public T getValue() {
        return value;
    }

    public String getKey() {
        return key;
    }

    public String getDisplayName() {
        return displayName;
    }

    public T getDefault() {
        return defaultValue;
    }

    public void render() {
        type.render(this);
    }

    public ISerializer<T> getSerializer() {
        return type.getSerializer();
    }

    public Option<T> ensureNamed(String fieldName) {
        if (key == null) {
            key = fieldName;
        }

        if (displayName == null) {
            displayName = key;
        }

        return this;

    }

}
