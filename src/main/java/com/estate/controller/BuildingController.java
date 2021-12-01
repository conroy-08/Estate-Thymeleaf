package com.estate.controller;

import com.estate.dto.BuildingDTO;
import com.estate.service.IBuildingService;
import com.estate.service.IUserService;
import com.estate.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;



@Controller(value = "buildingControllerOfAdmin")
public class BuildingController {


    private final IBuildingService buildingService;
    private final IUserService userService;

    @Autowired
    public BuildingController(IBuildingService buildingService, IUserService userService) {
        this.buildingService = buildingService;
        this.userService = userService;
    }

    @GetMapping("/building-list")
    public String buildingList(@ModelAttribute("modelSearch") BuildingDTO buildingDTO , Model model) {
        Pageable pageable = PageRequest.of(buildingDTO.getPage() - 1, buildingDTO.getLimit());
        buildingDTO.setTotalItems(buildingService.count(buildingDTO));
        buildingDTO.setTotalPage((int) Math.ceil((double) buildingDTO.getTotalItems() / buildingDTO.getLimit()));
        model.addAttribute("staffs", userService.getStaffs());
        model.addAttribute("buildingList",buildingService.findByCondition(buildingDTO, pageable));
        addObject(model);
        return "admin/building/list";
    }

    @GetMapping("/building-edit")
    public String buildingEdit(Model model) {
        BuildingDTO buildingDTO = new BuildingDTO();
        model.addAttribute("buildingEdit", buildingDTO);
        addObject(model);
        return "admin/building/edit";
    }

    @GetMapping("/building-edit-{id}")
    public String buildingEdit(@PathVariable(value = "id") Long id, Model model) {
        BuildingDTO buildingDTO = buildingService.findById(id);
        model.addAttribute("buildingEdit", buildingDTO);
        addObject(model);
        return "admin/building/edit";
    }

    @GetMapping("/building-view-{id}")
    public String buildingView(@PathVariable(value = "id") Long id , Model model) {
        BuildingDTO buildingDTO = buildingService.findById(id);
        model.addAttribute("buildingView", buildingDTO);
        addObject(model);
        return "admin/building/view";
    }

    private void addObject(Model model) {
        model.addAttribute("buildingTypes", DataUtils.getBuildingTypes());
        model.addAttribute("districts", DataUtils.getDistricts());
    }


}
