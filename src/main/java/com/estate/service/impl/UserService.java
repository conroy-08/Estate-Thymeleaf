package com.estate.service.impl;

import com.estate.constant.SystemConstant;
import com.estate.converter.UserConverter;
import com.estate.dto.UserDTO;
import com.estate.dto.response.StaffReponseDTO;
import com.estate.entity.RoleEntity;
import com.estate.entity.UserEntity;
import com.estate.repository.RoleRepository;
import com.estate.repository.UserRepository;
import com.estate.service.IUserService;
import javassist.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class UserService implements IUserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserConverter userConverter;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserDTO findOneByUserNameAndStatus(String name, int status) {
        return userConverter.convertToDto(userRepository.findOneByUserNameAndStatus(name, status));
    }

    @Override
    public Map<Long, String> getStaffs() {
        List<UserEntity> staffs = userRepository.findByStatusAndRoles_Code(1, "STAFF");
        Map<Long, String> results = staffs.stream().
                collect(Collectors.toMap(k -> k.getId(), v -> v.getFullName()));
        return results;
    }

    @Override
    public List<StaffReponseDTO> findAllStaff(Long buildingId, Long customerId) {
        List<UserEntity> userEntities = userRepository.findByStatusAndRoles_Code(SystemConstant.ACTIVE_STATUS, SystemConstant.ROLE_STAFF);
        List<StaffReponseDTO> staffDTOS = new ArrayList<>();
        for (UserEntity entity : userEntities) {
            StaffReponseDTO staffDTO = new StaffReponseDTO();
            staffDTO.setStaffId(entity.getId());
            staffDTO.setFullName(entity.getFullName());
            if (buildingId != null) {
                if (userRepository.existsByIdAndBuildingsId(staffDTO.getStaffId(), buildingId)) {
                    staffDTO.setBuildingChecked("checked");
                }
            }
            if (customerId != null) {
                if (userRepository.existsByIdAndCustomersId(staffDTO.getStaffId(), customerId)) {
                    staffDTO.setCustomerChecked("checked");
                }
            }
            staffDTOS.add(staffDTO);
        }
        return staffDTOS;
    }

    @Override
    public List<UserDTO> getUsers(String searchValue, Pageable pageable) {
        Page<UserEntity> users;
        if (StringUtils.isNotBlank(searchValue)) {
            users = userRepository.findByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(searchValue, searchValue, 0, pageable);
        } else {
            users = userRepository.findByStatusNot(0, pageable);
        }
        List<UserEntity> newsEntities = users.getContent();
        List<UserDTO> result = new ArrayList<>();
        for (UserEntity userEntity : newsEntities) {
            UserDTO userDTO = userConverter.convertToDto(userEntity);
            userDTO.setRoleCode(userEntity.getRoles().get(0).getCode());
            result.add(userDTO);
        }
        return result;
    }

    @Override
    public int getTotalItems(String searchValue) {
        int totalItem = 0;
        if (StringUtils.isNotBlank(searchValue)) {
            totalItem = (int) userRepository.countByUserNameContainingIgnoreCaseOrFullNameContainingIgnoreCaseAndStatusNot(searchValue, searchValue, 0);
        } else {
            totalItem = (int) userRepository.countByStatusNot(0);
        }
        return totalItem;
    }

    @Override
    @Transactional
    public UserDTO save(UserDTO newUser) throws NotFoundException {
        RoleEntity role = Optional.ofNullable(roleRepository.findOneByCode(newUser.getRoleCode()))
                .orElseThrow(() -> new NotFoundException("Role not found !"));
        UserEntity entity = userConverter.convertToEntity(newUser);
        entity.setRoles(Stream.of(role).collect(Collectors.toList()));
        entity.setStatus(1);
        entity.setPassword(passwordEncoder.encode(SystemConstant.PASSWORD_DEFAULT));
        return userConverter.convertToDto(userRepository.save(entity));
    }

    @Override
    @Transactional
    public UserDTO update(Long userId , UserDTO updateUser) throws NotFoundException {
        RoleEntity role =  Optional.ofNullable(roleRepository.findOneByCode(updateUser.getRoleCode()))
                .orElseThrow(() -> new NotFoundException("Role not found !"));
        UserEntity oldUser = userRepository.findById(userId).get();
        UserEntity userEntity = userConverter.convertToEntity(updateUser);
        userEntity.setUserName(oldUser.getUserName());
        userEntity.setStatus(oldUser.getStatus());
        userEntity.setRoles(Stream.of(role).collect(Collectors.toList()));
        userEntity.setPassword(oldUser.getPassword());
        return userConverter.convertToDto(userRepository.save(userEntity));
    }

    @Override
    public UserDTO findUserById(long id) {
        UserEntity entity = userRepository.findById(id).get();
        List<RoleEntity> roles = entity.getRoles();
        UserDTO dto = userConverter.convertToDto(entity);
        roles.forEach(item -> {
            dto.setRoleCode(item.getCode());
        });
        return dto;
    }
}
