package com.example.reporting.model.enums;

import com.example.reporting.support.EnumUtils;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;

import static org.apache.commons.lang3.StringUtils.trim;

@RequiredArgsConstructor
public enum Report {
    FRUIT("fruit-report"),
    INVENTORY("inventory-report");

    @JsonValue
    @Getter
    private final String description;

    @JsonCreator
    public static Report ofValue(final String value) {
        return EnumUtils.ofEnumValue(
                value, Report.class,
                i -> StringUtils.equalsAnyIgnoreCase(trim(value), i.description, i.name()));
    }

}
