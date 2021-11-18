package com.estate.service;

import com.estate.dto.PasswordDTO;
import com.estate.dto.UserDTO;
import com.estate.dto.response.StaffReponseDTO;
import javassist.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Map;

public interface IUserService {
    UserDTO findOneByUserNameAndStatus(String name, int status);
    UserDTO findByUserName(String userName) throws NotFoundException;
    Map<Long, String> getStaffs();
    List<StaffReponseDTO> findAllStaff(Long buildingId, Long customerId);
    List<UserDTO> getUsers(String searchValue ,  Pageable pageable);
    int getTotalItems(String searchValue);
    UserDTO save(UserDTO newUser) throws Exception;
    UserDTO update(Long userId , UserDTO updateUser) throws NotFoundException;
    void delete(List<Long> ids) throws NotFoundException;
    UserDTO findUserById(long id) throws NotFoundException;
    UserDTO resetPassword(long id) throws NotFoundException;
    UserDTO updateProfile(String userName , UserDTO userDTO) throws NotFoundException;
    void updatePassword(Long id , PasswordDTO passwordDTO) throws Exception;
    boolean userExists(String email);
}
