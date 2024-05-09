package com.example.reporting.service;

import com.example.reporting.entity.report.InventoryReportEntity;
import com.example.reporting.model.report.InventoryReportDto;
import com.example.reporting.repository.InventoryReportRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class InventoryReportService extends AbstractReport<InventoryReportDto, InventoryReportEntity> {

    public InventoryReportService(@NonNull final InventoryReportRepository repository) {
        super(repository);
    }
}
