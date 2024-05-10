package com.example.reporting.exception;

import java.io.Serial;

public final class UncheckedException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 8856650455054479643L;

    public UncheckedException(final String s) {
        super(s);
    }

    public UncheckedException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public UncheckedException(final Throwable cause) {
        super(cause);
    }

}