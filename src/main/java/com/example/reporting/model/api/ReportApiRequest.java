package com.example.reporting.model.api;

import com.example.reporting.model.enums.Comparator;
import com.example.reporting.model.enums.Format;
import com.example.reporting.model.enums.Mode;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;
import java.util.Objects;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIgnoreProperties(ignoreUnknown = true)
public class ReportApiRequest {

    private static final String COLUMNS_ARE_REQUIRED = "at least 2 columns are required";

    @NotBlank(message = "reportId is mandatory")
    private String reportId;
    private Mode mode = Mode.sync;
    private Format format;
    @NotNull(message = COLUMNS_ARE_REQUIRED)
    @Size(min = 2, message = COLUMNS_ARE_REQUIRED)
    private List<String> columns;
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

        private String column;
        private Comparator comparator;
        private String value;
    }
}
