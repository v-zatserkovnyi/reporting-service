package com.example.reporting.support;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

import com.example.reporting.exception.UncheckedException;
import org.apache.commons.lang3.reflect.FieldUtils;

import lombok.NonNull;

public class ReflectionUtils {

    private ReflectionUtils() {
        throw new UnsupportedOperationException();
    }

    public static <V> V getValue(@NonNull final String fieldName, @NonNull final Object target) {
        return get(FieldUtils.getDeclaredField(target.getClass(), fieldName, true), target);
    }

    @SuppressWarnings("unchecked")
    private static <V> V get(final Field field, final Object target) {
        try {
            if (isNotAccessible(field, target)) {
                field.setAccessible(true);
            }
            return (V) field.get(target);
        } catch (final IllegalAccessException e) {
            throw new UncheckedException(e.getMessage(), e);
        }
    }

    private static boolean isNotAccessible(final Field field, final Object target) {
        return (!Modifier.isPublic(field.getModifiers()) ||
                !Modifier.isPublic(field.getDeclaringClass().getModifiers()) ||
                Modifier.isFinal(field.getModifiers())) &&
                !field.canAccess(target);
    }
}
