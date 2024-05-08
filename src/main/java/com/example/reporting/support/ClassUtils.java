package com.example.reporting.support;

import lombok.NonNull;

public final class ClassUtils {

    private ClassUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> T cast(@NonNull final Object source, @NonNull final Class<T> clazz) {
        return clazz.cast(source);
    }
}
