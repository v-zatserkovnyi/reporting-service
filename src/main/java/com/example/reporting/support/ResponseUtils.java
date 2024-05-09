package com.example.reporting.support;

import org.springframework.http.ResponseEntity;

public final class ResponseUtils {

    private ResponseUtils() {
        throw new UnsupportedOperationException();
    }

    public static <T> ResponseEntity<T> ok(final T value) {
        return ResponseEntity.ok(value);
    }
}
