package com.example.reporting.controller;

import com.example.reporting.model.api.ReportApiRequest;
import com.example.reporting.model.report.FruitReportDto;
import com.example.reporting.service.FruitReportService;
import com.example.reporting.support.ProfileMethod;
import com.example.reporting.support.ResponseUtils;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class ReportController implements ApiController {

    public static final String REPORT_V1_CONTEXT = "/v1/reports";

    @NonNull
    private final FruitReportService service;

    @PostMapping(REPORT_V1_CONTEXT)
    @ProfileMethod
    public ResponseEntity<List<FruitReportDto>> createReport(@Valid @RequestBody final ReportApiRequest request) {

        return ResponseUtils.ok(service.getContent(request));
    }

}
