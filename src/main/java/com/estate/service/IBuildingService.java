package com.estate.service;

import com.estate.dto.BuildingDTO;
import com.estate.dto.request.AssignmentRequest;
import javassist.NotFoundException;

import org.springframework.data.domain.Pageable;

import java.io.IOException;
import java.util.List;

public interface IBuildingService {
   List<BuildingDTO> findByCondition(BuildingDTO buildingDTO ,  Pageable pageable);
   BuildingDTO saveOrUpdate(Long id ,BuildingDTO buildingDTO) throws NotFoundException, IOException;
   BuildingDTO findById(Long id);
   int count(BuildingDTO buildingDTO);
   void deleteBuilding(List<Long> ids) throws NotFoundException;
   void assignmentBuilding(AssignmentRequest request) throws Exception;
}
