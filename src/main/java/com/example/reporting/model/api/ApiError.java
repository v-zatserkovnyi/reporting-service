package com.example.reporting.model.api;

import lombok.Builder;
import lombok.NonNull;
import lombok.Singular;
import lombok.Value;

import java.util.List;

@Value
@Builder
public class ApiError {

    @NonNull
    String message;
    @Singular
    List<String> errors;
}
