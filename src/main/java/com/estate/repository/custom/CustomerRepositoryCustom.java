package com.estate.repository.custom;

import com.estate.builder.CustomerSearchBuilder;
import com.estate.entity.CustomerEntity;
import org.springframework.data.domain.Pageable;


import java.util.List;

public interface CustomerRepositoryCustom {
    List<CustomerEntity> findByCondition(CustomerSearchBuilder builder , Pageable pageable);
    Long count(CustomerSearchBuilder builder);
}
