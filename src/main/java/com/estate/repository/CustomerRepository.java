package com.estate.repository;

import com.estate.entity.CustomerEntity;
import com.estate.repository.custom.CustomerRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CustomerRepository extends CustomerRepositoryCustom, JpaRepository<CustomerEntity,Long> {
    long countByIdIn(List<Long> ids);
    void deleteByIdIn(List<Long> ids);
}
