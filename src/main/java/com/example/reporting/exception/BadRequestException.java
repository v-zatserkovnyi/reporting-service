package com.example.reporting.exception;

import java.io.Serial;

public class BadRequestException extends RuntimeException {

    @Serial
    private static final long serialVersionUID = 2664376562380963374L;

    public BadRequestException(final String s) {
        super(s);
    }

    public BadRequestException(final String s, final Throwable throwable) {
        super(s, throwable);
    }

    public BadRequestException(final Throwable cause) {
        super(cause);
    }

}
