package com.estate.repository;

import com.estate.entity.MyListEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface MyListRepository extends JpaRepository<MyListEntity,Long> {
    void deleteByBuildingidAndUserId(Long buildingId , Long userId);
    void  deleteMyListEntitiesByBuildingid(Long buildingId);
    List<MyListEntity> findMyListEntityByUserId(Long userId);
    boolean existsMyListEntitiesByBuildingid(Long buildingId);
    boolean existsMyListEntitiesByBuildingidAndAndUserId(Long buidlingId, Long userId);

}
