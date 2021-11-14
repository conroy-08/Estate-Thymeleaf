package com.estate.converter;

import com.estate.dto.MyListDTO;
import com.estate.entity.MyListEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class MyListConverter {

    @Autowired
    private ModelMapper modelMapper;

    public MyListDTO convertToDTO(MyListEntity entity){
        MyListDTO myListDTO = modelMapper.map(entity,MyListDTO.class);
        return myListDTO;
    }

    public MyListEntity convertToEntity(MyListDTO dto){
        MyListEntity myListEntity = modelMapper.map(dto,MyListEntity.class);
        return myListEntity;
    }
}
