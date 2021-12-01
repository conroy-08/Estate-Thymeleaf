package com.estate.controller;

import com.estate.dto.BuildingDTO;
import com.estate.service.IMyListService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;


import java.util.List;


@Controller(value = "mylistBuildingControllerOfAdmin")
public class MyListController {


    private final IMyListService myListService;

    @Autowired
    public MyListController(IMyListService myListService) {
        this.myListService = myListService;
    }

    @GetMapping("/building-mylist")
    public String myList(Model model) {
        List<BuildingDTO> buildingDTOS = myListService.getMyListBuilding();
        model.addAttribute("mylist", buildingDTOS);
        return "admin/building/mylist";
    }


}
