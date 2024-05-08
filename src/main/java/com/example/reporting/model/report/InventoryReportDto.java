package com.example.reporting.model.report;

import lombok.Data;

@Data
public class InventoryReportDto extends AbstractDSDto {

    private String itemName;
    private String warehouse;
    private Integer amount;
}
