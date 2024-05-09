package com.example.reporting.repository.query;

import com.example.reporting.entity.report.AbstractDSEntity;
import com.example.reporting.model.api.ReportApiRequest;
import jakarta.persistence.criteria.Predicate;
import lombok.NonNull;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public final class DSSpecs {

    private DSSpecs() {
        throw new UnsupportedOperationException();
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    public static <T extends AbstractDSEntity> Specification<T> createSpecification(@NonNull final List<ReportApiRequest.Filter> filters) {
        return (root, query, builder) -> {
            List<Predicate> predicates = new ArrayList<>();
            filters.forEach(filter -> {
                Predicate predicate;
                switch (filter.getComparator()) {
                    case less -> predicate = builder.lessThan(root.get(filter.getColumn()), (Comparable) filter.getValue());
                    case more -> predicate = builder.greaterThan(root.get(filter.getColumn()), (Comparable) filter.getValue());
                    case like -> predicate = builder.like(root.get(filter.getColumn()), "%" + filter.getValue() + "%");
                    default -> predicate = builder.equal(root.get(filter.getColumn()), filter.getValue());
                }
                predicates.add(predicate);
            });
            return builder.and(predicates.toArray(new Predicate[0]));
        };
    }


}
