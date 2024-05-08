package com.example.reporting.repository;

import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface DSRepository<T, ID> extends GenericRepository<T, ID> {
}
