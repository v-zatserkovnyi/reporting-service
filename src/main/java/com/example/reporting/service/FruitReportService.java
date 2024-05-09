package com.example.reporting.service;

import com.example.reporting.entity.report.FruitReportEntity;
import com.example.reporting.model.report.FruitReportDto;
import com.example.reporting.repository.FruitReportRepository;
import lombok.NonNull;
import org.springframework.stereotype.Service;

@Service
public class FruitReportService extends AbstractReport<FruitReportDto, FruitReportEntity> {

    public FruitReportService(@NonNull final FruitReportRepository repository) {
        super(repository);
    }
}
