package com.example.reporting.model.api;

import com.example.reporting.model.enums.Comparator;
import com.example.reporting.model.enums.Format;
import com.example.reporting.model.enums.Mode;
import com.example.reporting.model.enums.Report;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import jakarta.validation.Valid;
import jakarta.validation.constraints.AssertTrue;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Sort.Direction;

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

    @NotNull(message = "report is mandatory")
    private Report report;
    private Mode mode = Mode.sync;
    private Format format;
    @NotNull(message = COLUMNS_ARE_REQUIRED)
    @Size(min = 2, message = COLUMNS_ARE_REQUIRED)
    private List<String> columns;
    @Valid
    @NotNull(message = FILTERS_ARE_REQUIRED)
    @Size(min = 1, message = FILTERS_ARE_REQUIRED)
    private List<Filter> filters;
    private List<Sort> sorting;
    private List<String> grouping;

    @AssertTrue(message = "format is required for async mode only")
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

        @AssertTrue(message = "incompatible comparator and value")
        private boolean isValidComparator() {
            if (comparator == Comparator.like && !(value instanceof String)) return false;
            if (comparator == Comparator.equals) return true;
            return value instanceof Comparable;
        }
    }

    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @JsonInclude(JsonInclude.Include.NON_NULL)
    @JsonIgnoreProperties(ignoreUnknown = true)
    public static class Sort {

        @NotBlank(message = "column is mandatory")
        private String column;
        private Direction direction = Direction.ASC;
    }
}
