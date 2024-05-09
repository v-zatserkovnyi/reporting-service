package com.example.reporting.support;

import java.util.Arrays;
import java.util.function.Predicate;

import static com.example.reporting.support.FunctionUtils.supplyException;

public class EnumUtils {

    private EnumUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T extends Enum<T>> T ofEnumValue(final String value, final Class<T> clazz, final Predicate<T> predicate) {
        return Arrays.stream(clazz.getEnumConstants())
                .filter(predicate)
                .findFirst()
                .orElseThrow(supplyException(IllegalArgumentException.class, String.format("Value '%s' is not supported", value)));
    }
}
