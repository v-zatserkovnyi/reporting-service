package com.example.reporting.service.factory;

import com.example.reporting.model.enums.Report;
import com.example.reporting.service.AbstractReport;
import com.example.reporting.service.FruitReportService;
import com.example.reporting.service.InventoryReportService;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@SuppressWarnings({"rawtypes", "unchecked"})
@Service
@Slf4j
public class ReportFactory {

    private static final Map<Class<? extends AbstractReport>, Report> REPORT_CLASS_MAP = Map.ofEntries(
            Map.entry(FruitReportService.class, Report.FRUIT),
            Map.entry(InventoryReportService.class, Report.INVENTORY)
    );

    private final Map<Report, AbstractReport> reportHandlers;

    public ReportFactory(@NonNull final List<AbstractReport> reports) {
        reportHandlers = reports.stream()
                            .collect(Collectors.toMap(k ->
                                    REPORT_CLASS_MAP.get((Class<? extends AbstractReport>) AopUtils.getTargetClass(k)), Function.identity()));
    }

    public AbstractReport getReportService(@NonNull final Report report) {
        return reportHandlers.get(report);
    }
}
