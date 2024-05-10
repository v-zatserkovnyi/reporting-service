package com.example.reporting.service;

import com.example.reporting.entity.report.AbstractDSEntity;
import com.example.reporting.model.api.ReportApiRequest;
import com.example.reporting.model.report.AbstractDSDto;
import com.example.reporting.repository.DSRepository;
import com.example.reporting.repository.query.DSSpecs;
import com.example.reporting.support.DTOUtils;
import lombok.NonNull;
import org.apache.commons.collections4.CollectionUtils;
import org.springframework.data.jpa.domain.Specification;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.reporting.support.ReflectionUtils.getValue;

public abstract class AbstractReport<T extends AbstractDSDto, E extends AbstractDSEntity> {

    private final DSRepository<E, Long> repository;
    private final Class<T> dtoClass;

    @SuppressWarnings("unchecked")
    protected AbstractReport(final DSRepository<E, Long> repository) {
        this.repository = repository;
        Type superclass = getClass().getGenericSuperclass();
        this.dtoClass = (Class<T>) ((ParameterizedType) superclass).getActualTypeArguments()[0];
    }

    public Object getContent(final ReportApiRequest request) {

        final Specification<E> specs = DSSpecs.createSpecification(request.getFilters());
        final List<E> entities;
        if (CollectionUtils.isEmpty(request.getSorting()))
            entities = repository.findAll(specs);
        else
            entities = repository.findAll(specs, DSSpecs.createSort(request.getSorting()));

        final List<T> dtos = entities.stream()
                .map(e -> DTOUtils.mapFields(e, dtoClass, request.getColumns()))
                .toList();

        return groupData(dtos, request.getGrouping());
    }

    private Object groupData(@NonNull final List<T> data, final List<String> groupByFields) {
        if (CollectionUtils.isEmpty(groupByFields)) return data;

        // Obtain the first field to group by and remove it from the list.
        // I am not using remove by index method since the List is immutable from api request
        String groupBy = groupByFields.get(0);
        List<String> remainingGroupByFields = new ArrayList<>(groupByFields.subList(1, groupByFields.size()));

        // Group by the first field
        Map<Object, List<T>> groupedData = data.stream()
                .collect(Collectors.groupingBy(dto -> getValue(groupBy, dto)));

        // If there are more fields to group by, apply this method recursively
        if (!remainingGroupByFields.isEmpty()) {
            Map<Object, Object> nestedGroupedData = new HashMap<>();
            for (Map.Entry<Object, List<T>> entry : groupedData.entrySet()) {
                nestedGroupedData.put(entry.getKey(), groupData(entry.getValue(), remainingGroupByFields));
            }
            return nestedGroupedData;
        }

        return groupedData;
    }
}
