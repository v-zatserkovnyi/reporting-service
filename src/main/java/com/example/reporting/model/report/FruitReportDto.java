package com.example.reporting.model.report;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class FruitReportDto extends AbstractDSDto {

    private String fruitName;
    private String deliveryCompany;
    private LocalDate deliveryDate;
    private Integer amount;
    private BigDecimal price;
}
