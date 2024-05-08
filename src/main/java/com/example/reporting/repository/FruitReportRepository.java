package com.example.reporting.repository;

import com.example.reporting.entity.report.FruitReportEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface FruitReportRepository extends DSRepository<FruitReportEntity, Long> {
}
