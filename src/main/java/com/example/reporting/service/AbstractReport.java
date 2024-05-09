package com.example.reporting.service;

import com.example.reporting.entity.report.AbstractDSEntity;
import com.example.reporting.model.api.ReportApiRequest;
import com.example.reporting.model.report.AbstractDSDto;
import com.example.reporting.repository.DSRepository;
import com.example.reporting.repository.query.DSSpecs;
import com.example.reporting.support.DTOUtils;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public abstract class AbstractReport<T extends AbstractDSDto, E extends AbstractDSEntity> {

    private final DSRepository<E, Long> repository;
    private final Class<T> dtoClass;

    @SuppressWarnings("unchecked")
    protected AbstractReport(DSRepository<E, Long> repository) {
        this.repository = repository;
        Type superclass = getClass().getGenericSuperclass();
        this.dtoClass = (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    public List<T> getContent(final ReportApiRequest request) {

        final Specification<E> specs = DSSpecs.createSpecification(request.getFilters());
        final List<E> entities = repository.findAll(specs);

        return entities.stream()
                .map(e -> DTOUtils.mapFields(e, dtoClass, request.getColumns()))
                .toList();
    }
}
