package com.example.reporting.model.report;

import java.math.BigDecimal;
import java.time.LocalDate;

public class FruitReportDto extends AbstractDSDto {

    private String fruitName;
    private String deliveryCompany;
    private LocalDate deliveryDate;
    private Integer amount;
    private BigDecimal price;
}
