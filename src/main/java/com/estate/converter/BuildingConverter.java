package com.estate.converter;


import com.estate.dto.BuildingDTO;
import com.estate.entity.BuildingEntity;
import com.estate.entity.RentAreaEntity;
import com.estate.enums.DistrictsEnum;
import org.apache.commons.lang.StringUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class BuildingConverter {
    @Autowired
    private ModelMapper modelMapper;

    public BuildingDTO convertToDTO(BuildingEntity entity) {
        BuildingDTO dto = modelMapper.map(entity, BuildingDTO.class);
        List<Float> rentAreas = entity.getRentAreas().stream()
                .map(RentAreaEntity::getValue).collect(Collectors.toList());
        if(rentAreas.size() > 0){
            String rentArea = rentAreas.toString();
            dto.setRentArea(rentArea.substring(1, rentArea.length()-1));
        }
        if (StringUtils.isNotBlank(entity.getType())){
            dto.setBuildingTypes(entity.getType().split(","));
        }
        dto.setStaffId(entity.getId());
        dto.setAddress(setAddress(dto));
        return dto;
    }



    public BuildingEntity convertToEntity(BuildingDTO dto) {
        BuildingEntity entity = modelMapper.map(dto, BuildingEntity.class);
        return entity;
    }
 

    public String setAddress(BuildingDTO dto){
        StringBuilder sb = new StringBuilder("");
        Map<String, String> districts = Stream.of(DistrictsEnum.values())
                                       .collect(Collectors.toMap(k -> k.toString(), v -> v.getDistrictValue()));
        if (districts.containsKey(dto.getDistrict())) {
            sb.append(districts.get(dto.getDistrict()));
        }
        String[] firstArray = {dto.getStreet() , dto.getWard(), sb.toString()};
        firstArray = Arrays.stream(firstArray)
                .filter(s -> (s != null && s.length() > 0))
                .toArray(String[]::new);
        String address = Arrays.stream(firstArray).map(item -> item + " ")
                .collect(Collectors.joining(", "));
        dto.setDistrict(sb.toString());
        return address;

    }


}
