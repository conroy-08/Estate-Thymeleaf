package com.estate.service.impl;

import com.estate.converter.RoleConverter;
import com.estate.dto.RoleDTO;
import com.estate.entity.RoleEntity;
import com.estate.repository.RoleRepository;
import com.estate.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class RoleService implements IRoleService {

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private RoleConverter roleConverter;

    @Override
    public List<RoleDTO> findAll() {
        List<RoleEntity> roleEntities = roleRepository.findAll();
        List<RoleDTO> result = new ArrayList<>();
        roleEntities.forEach(
                item ->{
                    RoleDTO roleDTO = roleConverter.convertToDto(item);
                    result.add(roleDTO);
                }
        );
        return result;
    }

    @Override
    public Map<String, String> getRoles() {
        Map<String,String> roleTerm = new HashMap<>();
        List<RoleEntity> roleEntity = roleRepository.findAll();
        roleEntity.forEach(entity ->{
            RoleDTO roleDTO = roleConverter.convertToDto(entity);
            roleTerm.put(roleDTO.getCode(), roleDTO.getName());
        });
        return roleTerm;
    }
}
