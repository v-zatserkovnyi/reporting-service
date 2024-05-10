package com.example.reporting.controller.support;

import com.example.reporting.exception.BadRequestException;
import com.example.reporting.model.api.ApiError;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.function.Function;

import static com.example.reporting.support.ClassUtils.cast;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    private static final String ERROR_MESSAGE = "{} has happened due to the following issue:";

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> any(@NonNull final Exception exception) {
        log.error(ERROR_MESSAGE, HttpStatus.INTERNAL_SERVER_ERROR.name(), exception);

        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiError.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ApiError> bad(@NonNull final RuntimeException exception) {
        log.warn(ERROR_MESSAGE, HttpStatus.BAD_REQUEST.name(), exception);

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(ApiError.builder().message(exception.getMessage()).build());
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            BindException.class
    })
    public ResponseEntity<ApiError> methodArgsNotValid(@NonNull final Exception exception) {
        log.warn(ERROR_MESSAGE, HttpStatus.BAD_REQUEST.name(), exception);

        final BindingResult bindingResult;
        if (exception.getClass() == MethodArgumentNotValidException.class) {
            bindingResult = cast(exception, MethodArgumentNotValidException.class).getBindingResult();
        } else {
            bindingResult = cast(exception, BindException.class).getBindingResult();
        }

        final Function<FieldError, String> errorMessageBuilder =
                fieldError -> String.format("'%s' %s", fieldError.getField(), fieldError.getDefaultMessage());
        final var fieldErrors = bindingResult.getFieldErrors()
                .parallelStream()
                .map(errorMessageBuilder)
                .toList();
        final var error = ApiError.builder()
                .message("Invalid values supplied")
                .errors(fieldErrors)
                .build();

        return ResponseEntity.badRequest()
                .contentType(MediaType.APPLICATION_JSON)
                .body(error);
    }
}
