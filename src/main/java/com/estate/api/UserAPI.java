package com.estate.api;

import com.estate.constant.SystemConstant;
import com.estate.dto.PasswordDTO;
import com.estate.dto.UserDTO;
import com.estate.service.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "userApiOfAdmin")
@RequestMapping("/api/user")
public class UserAPI {

    @Autowired
    private IUserService userService;

    @PostMapping()
    public ResponseEntity<UserDTO> createUsers(@RequestBody UserDTO newUser) throws NotFoundException {
        return ResponseEntity.ok(userService.save(newUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUsers(@PathVariable("id") Long id, @RequestBody UserDTO userDTO) throws NotFoundException {
        return ResponseEntity.ok(userService.update(id, userDTO));
    }


    @DeleteMapping
    public void deleteUsers(@RequestBody List<Long> ids) throws NotFoundException {
        userService.delete(ids);
    }

    @PutMapping("/password-{id}/reset")
    public ResponseEntity<UserDTO> resetPassword(@PathVariable("id") long id) throws NotFoundException {
        return ResponseEntity.ok(userService.resetPassword(id));
    }

    @PutMapping("/change-password-{id}")
    public ResponseEntity<String> changePassword(@PathVariable("id") Long id , @RequestBody PasswordDTO passwordDTO){
        try {
            userService.updatePassword(id, passwordDTO);
            return ResponseEntity.ok(SystemConstant.UPDATE_SUCCESS);
        } catch ( Exception e) {
            return ResponseEntity.ok(e.getMessage());
        }
    }

}
