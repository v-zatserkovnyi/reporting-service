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

    @NonNull
    private final ReportFactory factory;

    public ReportApiResponse createReport(@NonNull final ReportApiRequest request) {
        // checking role and permissions is out of the scope for this project
        final var content = factory.getReportService(request.getReport()).getContent(request);

        return ReportApiResponse.builder()
                .content(content)
                .build();
    }
}
