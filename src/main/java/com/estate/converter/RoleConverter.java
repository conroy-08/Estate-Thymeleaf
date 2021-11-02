package com.estate.converter;


import com.estate.dto.RoleDTO;
import com.estate.entity.RoleEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RoleConverter {
	
	@Autowired
	private ModelMapper modelMapper;
	
	public RoleDTO convertToDto(RoleEntity entity) {
		RoleDTO result = modelMapper.map(entity, RoleDTO.class);
        return result;
    }

    public RoleEntity convertToEntity(RoleDTO dto) {
    	RoleEntity result = modelMapper.map(dto, RoleEntity.class);
        return result;
    }
}
