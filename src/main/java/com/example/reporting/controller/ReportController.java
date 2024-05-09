package com.example.reporting.controller;

import com.example.reporting.model.api.ReportApiRequest;
import com.example.reporting.model.api.ReportApiResponse;
import com.example.reporting.service.facade.ReportFacade;
import com.example.reporting.support.ProfileMethod;
import com.example.reporting.support.ResponseUtils;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ReportController implements ApiController {

    public static final String REPORT_V1_CONTEXT = "/v1/reports";

    @NonNull
    private final ReportFacade facade;

    @PostMapping(REPORT_V1_CONTEXT)
    @ProfileMethod
    public ResponseEntity<ReportApiResponse> createReport(@Valid @RequestBody final ReportApiRequest request) {

        return ResponseUtils.ok(facade.createReport(request));
    }
}
