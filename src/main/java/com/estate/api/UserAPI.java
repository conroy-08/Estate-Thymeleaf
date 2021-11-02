package com.estate.api;

import com.estate.dto.UserDTO;
import com.estate.service.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

}
