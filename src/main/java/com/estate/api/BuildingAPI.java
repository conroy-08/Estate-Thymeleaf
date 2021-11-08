package com.estate.api;

import com.estate.dto.BuildingDTO;
import com.estate.dto.request.AssignmentRequest;
import com.estate.dto.response.ResponseDTO;
import com.estate.dto.response.StaffReponseDTO;
import com.estate.service.IBuildingService;
import com.estate.service.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.io.IOException;
import java.util.List;

@RestController(value = "buildingApiOfAdmin")
@RequestMapping("/api/building")
public class BuildingAPI {

    @Autowired
    private IBuildingService buildingService;


    @Autowired
    private IUserService userService;

    @GetMapping("/{buildingId}/staffs")
    public ResponseEntity<ResponseDTO> loadStaff(@PathVariable("buildingId") Long buildingId) {
        try {
            ResponseDTO result = new ResponseDTO();
            List<StaffReponseDTO> staffs = userService.findAllStaff(buildingId, null);
            result.setData(staffs);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/assignment")
    public void assignmentBuilding(@RequestBody AssignmentRequest assignmentRequest) throws Exception {
        buildingService.assignmentBuilding(assignmentRequest);
    }

    @PostMapping()
    public ResponseEntity<BuildingDTO> createBuilding(@RequestBody BuildingDTO newBuilding) throws NotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.saveOrUpdate(newBuilding.getId(), newBuilding));
    }

    @PutMapping
    public ResponseEntity<BuildingDTO> updateBuilding(@RequestBody BuildingDTO updateBuilding) throws NotFoundException, IOException {
        return ResponseEntity.status(HttpStatus.OK).body(buildingService.saveOrUpdate(updateBuilding.getId(), updateBuilding));
    }


    @DeleteMapping
    public void deleteBuildings(@RequestBody List<Long> ids) throws NotFoundException {
        buildingService.deleteBuilding(ids);
    }


}
