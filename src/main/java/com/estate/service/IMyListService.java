package com.estate.service;

import com.estate.dto.BuildingDTO;
import com.estate.dto.MyListDTO;

import java.util.List;

public interface IMyListService {
    List<BuildingDTO> getMyListBuilding();
    MyListDTO save(Long buildingId);
    void delete(List<Long> ids) ;

}
