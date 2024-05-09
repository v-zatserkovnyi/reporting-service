package com.example.reporting.model.api;

import com.example.reporting.model.enums.Comparator;
import com.example.reporting.model.enums.Format;
import com.example.reporting.model.enums.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportApiRequest {

    private static final String COLUMNS_ARE_REQUIRED = "at least 2 columns are required";
    private static final String FILTERS_ARE_REQUIRED = "filters are required";

    @NotBlank(message = "reportId is mandatory")
    private String reportId;
    private Mode mode = Mode.sync;
    private Format format;
    @NotNull(message = COLUMNS_ARE_REQUIRED)
    @Size(min = 2, message = COLUMNS_ARE_REQUIRED)
    private List<String> columns;
    @Valid
    @NotNull(message = FILTERS_ARE_REQUIRED)
    @Size(min = 1, message = FILTERS_ARE_REQUIRED)
    private List<Filter> filters;
    private List<String> sorting;
    private List<String> grouping;

    @AssertTrue
    private boolean isValidMode() {
        return (Mode.sync == mode && Objects.isNull(format)) || (Mode.async == mode && Objects.nonNull(format));
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Filter {

        @NotBlank(message = "column is mandatory")
        private String column;
        private Comparator comparator = Comparator.equals;
        @NotNull(message = "value is mandatory")
        private Object value;

        @AssertTrue
        private boolean isValidComparator() {
            if (comparator == Comparator.like && !(value instanceof String)) return false;
            if (comparator == Comparator.equals) return true;
            return value instanceof Comparable;
        }
    }
}
