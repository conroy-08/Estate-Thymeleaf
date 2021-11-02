package com.estate.repository;

import com.estate.entity.BuildingEntity;
import com.estate.repository.custom.BuildingRepositoryCustom;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BuildingRepository extends BuildingRepositoryCustom,JpaRepository<BuildingEntity,Long> {
    long countByIdIn(List<Long> ids);
}
