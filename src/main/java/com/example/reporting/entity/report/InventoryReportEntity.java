package com.example.reporting.entity.report;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "inventory_report", schema = "report")
public class InventoryReportEntity extends AbstractDSEntity {

    @Id
    @Column(name="inventory_report_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String itemName;
    private String warehouse;
    private Integer amount;
}
