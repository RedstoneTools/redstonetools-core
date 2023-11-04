package tools.redstone.config;

import tools.redstone.config.format.IConfigSection;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

public class ReflectionUtils {
    private static Stream<Field> getOptionFields(Class<?> cls) {
        return Arrays.stream(cls.getFields()).filter(field -> Option.class.isAssignableFrom(field.getType()))
                .peek(field -> {
                    // TODO: Maybe options should be private static final?
                    //  Not sure if making them public is a good idea
                    var modifiers = field.getModifiers();
                    if (!Modifier.isPublic(modifiers) || !Modifier.isStatic(modifiers)
                            || !Modifier.isFinal(modifiers)) {
                        throw new RuntimeException("Option field " + field.getName() + " of class " + cls.getName()
                                + " is not public static final");
                    }
                });
    }

    private static Stream<Option<?>> getOptions(Class<?> cls) {
        return getOptionFields(cls)
                .map(optionField -> {
                    try {
                        return (Option<?>) optionField.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to get value of field " + optionField.getName(), e);
                    }
                });
    }

    public static void registerOptions(Class<?> featureClass) {
        getOptionFields(featureClass)
                .forEach(optionField -> {
                    Option<?> option;
                    try {
                        option = (Option<?>) optionField.get(null);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException("Failed to get value of field " + optionField.getName(), e);
                    }

                    option.ensureNamed(optionField.getName());
                });
    }

    public static void loadOptions(Class<?> cls, Optional<IConfigSection> config) {
        config.ifPresent(c -> {
            getOptions(cls)
                    .forEach(option -> option.loadValue(c));
        });
    }
}
