package com.example.reporting.repository;

import com.example.reporting.entity.report.InventoryReportEntity;
import org.springframework.stereotype.Repository;

@Repository
public interface InventoryReportRepository extends DSRepository<InventoryReportEntity, Long> {
}
