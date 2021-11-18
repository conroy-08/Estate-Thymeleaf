package com.estate.api;

import com.estate.dto.MyListDTO;
import com.estate.service.IMyListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "mylistApiOfAdmin")
@RequestMapping("/api/mylist")
public class MyListAPI {

    private final IMyListService myListService;

    @Autowired
    public MyListAPI(IMyListService myListService) {
        this.myListService = myListService;
    }


    @PostMapping(value = "/{id}")
    public ResponseEntity<MyListDTO> saveMyList(@PathVariable("id") Long buildingId) {
        return ResponseEntity.status(HttpStatus.OK).body(myListService.save(buildingId));
    }

    @DeleteMapping
    public void deleteBuildings(@RequestBody List<Long> ids) {
        myListService.delete(ids);
    }


}
