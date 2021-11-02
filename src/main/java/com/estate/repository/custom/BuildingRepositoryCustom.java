package com.estate.repository.custom;

import com.estate.builder.BuildingSearchBuilder;
import com.estate.entity.BuildingEntity;

import org.springframework.data.domain.Pageable;
import java.util.List;

public interface BuildingRepositoryCustom {
    List<BuildingEntity> findByCondition(BuildingSearchBuilder builder , Pageable pageable);
    Long count(BuildingSearchBuilder builder);
}
