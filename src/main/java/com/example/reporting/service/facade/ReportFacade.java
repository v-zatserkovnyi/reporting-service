package com.example.reporting.service.facade;

import com.example.reporting.model.api.ReportApiRequest;
import com.example.reporting.model.api.ReportApiResponse;
import com.example.reporting.service.factory.ReportFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReportFacade {

    private final ReportFactory factory;

    public ReportApiResponse createReport(@NonNull final ReportApiRequest request) {

        final var content = factory.getReportService(request.getReport()).getContent(request);

        return ReportApiResponse.builder()
                .content(content)
                .build();
    }
}
