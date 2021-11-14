package com.estate.service.impl;


import com.estate.converter.BuildingConverter;
import com.estate.converter.MyListConverter;
import com.estate.dto.BuildingDTO;
import com.estate.dto.MyListDTO;
import com.estate.entity.BuildingEntity;
import com.estate.entity.MyListEntity;
import com.estate.repository.BuildingRepository;
import com.estate.repository.MyListRepository;
import com.estate.service.IMyListService;
import com.estate.utils.SecurityUtils;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class MyListService implements IMyListService {

    @Autowired
    private MyListRepository myListRepository;

    @Autowired
    private BuildingRepository buildingRepository;

    @Autowired
    private BuildingConverter buildingConverter;

    @Autowired
    private MyListConverter myListConverter;

    @Override
    public List<BuildingDTO> getMyListBuilding() {
        Long staffId = SecurityUtils.getPrincipal().getId();
        List<MyListEntity> myListEntities = myListRepository.findMyListEntityByUserId(staffId);
        List<BuildingDTO> list = new ArrayList<>();
        List<BuildingEntity> entities = new ArrayList<>();
        List<Long> buildingIds = new ArrayList<>();
        myListEntities.forEach(item->{
            buildingIds.add(item.getBuildingid());
        });
        List<Long> rs = buildingIds.stream().distinct().collect(Collectors.toList());
        rs.forEach(item ->{
            try {
                BuildingEntity x =  Optional.ofNullable(buildingRepository.findById(item).get())
                        .orElseThrow(() -> new NotFoundException("Customer not found !"));
                entities.add(x);
            } catch (NotFoundException e) {
                e.printStackTrace();
            }
        });
        entities.forEach(i ->{
            BuildingDTO dto = buildingConverter.convertToDTO(i);
            list.add(dto);
        });
        return list;
    }

    @Override
    public MyListDTO save(Long buildingId) {
        Long userId = SecurityUtils.getPrincipal().getId();
        MyListEntity myListEntity = new MyListEntity();
        myListEntity.setBuildingid(buildingId);
        myListEntity.setUserId(userId);
        return myListConverter.convertToDTO(myListRepository.save(myListEntity));
    }


    @Override
    @Transactional
    public void delete(List<Long> ids)  {
        Long userId = SecurityUtils.getPrincipal().getId();
        for ( Long id : ids){
            myListRepository.deleteByBuildingidAndUserId(id,userId);
        }
    }


}
