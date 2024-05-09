package com.example.reporting.support;

import java.util.function.Supplier;

public final class FunctionUtils {

    private FunctionUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T extends RuntimeException> Supplier<T> supplyException(final Class<T> exception, final String message) {
        return () -> ClassUtils.newInstance(exception, message);
    }
}
