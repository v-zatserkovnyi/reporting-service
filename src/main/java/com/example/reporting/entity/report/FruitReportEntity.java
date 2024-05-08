package com.example.reporting.entity.report;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "fruit_report", schema = "report")
public class FruitReportEntity extends AbstractDSEntity {

    @Id
    @Column(name="fruit_report_id", updatable = false)
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String fruitName;
    private String deliveryCompany;
    private LocalDate deliveryDate;
    private Integer amount;
    private BigDecimal price;
}
