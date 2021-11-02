package com.estate.service;

import com.estate.dto.UserDTO;
import com.estate.dto.response.StaffReponseDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IUserService {
    UserDTO findOneByUserNameAndStatus(String name, int status);
    Map<Long, String> getStaffs();
    List<StaffReponseDTO> findAllStaff(Long buildingId, Long customerId);
    List<UserDTO> getUsers(String searchValue ,  Pageable pageable);
    int getTotalItems(String searchValue);
    UserDTO save(UserDTO newUser) throws NotFoundException;
    UserDTO update(Long userId , UserDTO updateUser) throws NotFoundException;
    UserDTO findUserById(long id);

}
