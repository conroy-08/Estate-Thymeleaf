package com.estate.service.impl;


import com.estate.builder.BuildingSearchBuilder;
import com.estate.constant.SystemConstant;
import com.estate.converter.BuildingConverter;
import com.estate.dto.BuildingDTO;
import com.estate.dto.request.AssignmentRequest;
import com.estate.entity.BuildingEntity;
import com.estate.entity.RentAreaEntity;
import com.estate.entity.UserEntity;
import com.estate.repository.BuildingRepository;
import com.estate.repository.MyListRepository;
import com.estate.repository.RentAreaRepository;
import com.estate.repository.UserRepository;
import com.estate.service.IBuildingService;
import com.estate.utils.SecurityUtils;
import javassist.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
public class BuildingService implements IBuildingService {

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingConverter buildingConverter;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RentAreaRepository areaRepository;

    @Autowired
    private MyListRepository myListRepository;

    @Override
    public List<BuildingDTO> findByCondition(BuildingDTO buildingDTO , Pageable pageable) {
        BuildingSearchBuilder builder = initBuildingBuilder(buildingDTO);
        List<BuildingDTO> buildingDTOS = new ArrayList<>();
        List<BuildingEntity> entities = buildingRepository.findByCondition(builder , pageable);
        entities.forEach(
                item -> {
                    BuildingDTO dto = buildingConverter.convertToDTO(item);
                    buildingDTOS.add(dto);
                }
        );
        return buildingDTOS;
    }

    @Override
    @Transactional
    public BuildingDTO saveOrUpdate(Long id, BuildingDTO buildingDTO) throws NotFoundException {
        BuildingEntity buildingEntity ;
        if (id != null && id > 0) {
            BuildingEntity oldBuilding = Optional.ofNullable(buildingRepository.findById(id).get())
                    .orElseThrow(() -> new NotFoundException("Building not found !"));
            buildingEntity = buildingConverter.convertToEntity(buildingDTO);
            buildingEntity.setStaffs(oldBuilding.getStaffs());
            areaRepository.deleteByBuildingId(id);
        } else {
            buildingEntity = buildingConverter.convertToEntity(buildingDTO);
        }
        if (buildingDTO.getBuildingTypes().length > 0) {
            buildingEntity.setType(StringUtils.join(buildingDTO.getBuildingTypes(), ","));
        }
        List<RentAreaEntity> areas = new ArrayList<>();
        if (StringUtils.isNotBlank(buildingDTO.getRentArea())) {
            for (String item : buildingDTO.getRentArea().split(",")) {
                RentAreaEntity rentAreaEntity = new RentAreaEntity();
                if (Float.parseFloat(item.trim()) > 0) {
                    rentAreaEntity.setValue(Float.parseFloat(item.trim()));
                }
                rentAreaEntity.setBuilding(buildingEntity);
                areas.add(rentAreaEntity);
            }
            buildingEntity.setRentAreas(areas);

        }
        buildingEntity = buildingRepository.save(buildingEntity);
        return buildingConverter.convertToDTO(buildingEntity);
    }

    @Override
    public BuildingDTO findById(Long id) {
        BuildingEntity buildingEntity = Optional.ofNullable(buildingRepository.findById(id).get())
                .orElseThrow(() -> new NullPointerException("Building not found !"));
        return buildingConverter.convertToDTO(buildingEntity);
    }

    @Override
    public int count(BuildingDTO buildingDTO) {
        BuildingSearchBuilder builder = initBuildingBuilder(buildingDTO);
        return  buildingRepository.count(builder).intValue();
    }


    @Override
    @Transactional
    public void deleteBuilding(List<Long> ids) throws NotFoundException {
        long count = buildingRepository.countByIdIn(ids);
        if (ids.size() != count) {
            throw new NotFoundException("Building not found !");
        }
        for ( Long id : ids){
            if (myListRepository.existsMyListEntitiesByBuildingid(id)){
               myListRepository.deleteMyListEntitiesByBuildingid(id);
            }
            buildingRepository.deleteById(id);
        }
    }

    @Override
    @Transactional
    public void assignmentBuilding(AssignmentRequest request) throws Exception {
        BuildingEntity buildingEntity = Optional.ofNullable(buildingRepository.findById(request.getId()).get())
                .orElseThrow(() -> new NotFoundException("Building not found !"));
        List<Long> oldStaffs = new ArrayList<>();
        buildingEntity.getStaffs().forEach( i->{
            oldStaffs.add(i.getId());
        });
        List<Long> ids = request.getUserIds();
        List<Long> list = oldStaffs.stream().filter(i->!ids.contains(i)).collect(Collectors.toList());
        list.forEach( item ->{
            if (myListRepository.existsMyListEntitiesByBuildingidAndAndUserId(request.getId(), item)){
                myListRepository.deleteByBuildingidAndUserId(request.getId(), item);
            }
        });
        Long count = userRepository.countByIdIn(ids);
        if (count != ids.size()) {
            throw new Exception("Something wrong !");
        } else {
            buildingEntity.setStaffs(userRepository.findAllById(ids));
            buildingRepository.save(buildingEntity);
        }

    }

    private BuildingSearchBuilder initBuildingBuilder(BuildingDTO model) {
        Long staffId;
        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            staffId = SecurityUtils.getPrincipal().getId();
        } else {
            staffId = model.getStaffId();
        }
        BuildingSearchBuilder builder = new BuildingSearchBuilder.Builder().setName(model.getName())
                .setStreet(model.getStreet())
                .setWard(model.getWard())
                .setNumberOfBasement(model.getNumberOfBasement())
                .setDistrict(model.getDistrict())
                .setDirection(model.getDirection())
                .setLevel(model.getLevel())
                .setStaffId(staffId)
                .setBuildingArea(model.getBuildingArea())
                .setCostRentFrom(model.getCostRentFrom()).setCostRentTo(model.getCostRentTo())
                .setAreaRentFrom(model.getAreaRentFrom()).setAreaRentTo(model.getAreaRentTo())
                .setManagerName(model.getManagerName())
                .setManagerPhone(model.getManagerPhone()).setBuildingTypes(model.getBuildingTypes()).build();
        return builder;
    }
}
