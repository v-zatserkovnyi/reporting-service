package com.example.reporting.support;

import lombok.NonNull;
import org.apache.commons.lang3.reflect.ConstructorUtils;

public final class ClassUtils {

    private ClassUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> T cast(@NonNull final Object source, @NonNull final Class<T> clazz) {
        return clazz.cast(source);
    }

    public static <T> T newInstance(@NonNull final Class<T> clazz, final Object... args) {
        try {
            return ConstructorUtils.invokeConstructor(clazz, args);
        } catch (final ReflectiveOperationException e) {
            throw new UnsupportedOperationException(e.getMessage(), e);
        }
    }
}
