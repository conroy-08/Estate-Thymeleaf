package com.estate.controller;

import com.estate.dto.BuildingDTO;
import com.estate.service.IMyListService;

import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;


@Controller(value = "mylistBuildingControllerOfAdmin")
public class MyListController {


    @Autowired
    private IMyListService myListService;

    @GetMapping("/building-mylist")
    public ModelAndView myList() throws NotFoundException {
        ModelAndView mav = new ModelAndView("admin/building/mylist");
        List<BuildingDTO> buildingDTOS = myListService.getMyListBuilding();
        mav.addObject("mylist",buildingDTOS);
        return mav;
    }


}
