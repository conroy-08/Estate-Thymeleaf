package com.estate.controller;

import com.estate.dto.BuildingDTO;
import com.estate.service.IBuildingService;
import com.estate.service.IUserService;
import com.estate.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;




@Controller(value = "buildingControllerOfAdmin")
public class BuildingController {

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IUserService userService;

    @RequestMapping(value = "/building-list", method = RequestMethod.GET)
    public ModelAndView buildingList(@ModelAttribute("modelSearch") BuildingDTO buildingDTO ) {
        ModelAndView mav = new ModelAndView("admin/building/list");
        Pageable pageable =  PageRequest.of(buildingDTO.getPage()-1,buildingDTO.getLimit());
        buildingDTO.setTotalItems(buildingService.count(buildingDTO));
        buildingDTO.setTotalPage((int) Math.ceil((double) buildingDTO.getTotalItems() / buildingDTO.getLimit()));
        buildingDTO.setListResult(buildingService.findByCondition(buildingDTO,pageable));
        mav.addObject("staffs", userService.getStaffs());
        addObject(mav);
        return mav;
    }

    @RequestMapping(value = "/building-edit", method = RequestMethod.GET)
    public ModelAndView buildingEdit() {
        ModelAndView mav = new ModelAndView("admin/building/edit");
        BuildingDTO buildingDTO = new BuildingDTO();
        mav.addObject("buildingEdit", buildingDTO);
        addObject(mav);
        return mav;
    }

    @RequestMapping(value = "/building-edit-{id}", method = RequestMethod.GET)
    public ModelAndView buildingEdit(@PathVariable(value = "id") Long id)  {
        ModelAndView mav = new ModelAndView("admin/building/edit");
        BuildingDTO buildingDTO = buildingService.findById(id);
        mav.addObject("buildingEdit", buildingDTO);
        addObject(mav);
        return mav;
    }

    @RequestMapping(value = "/building-view-{id}", method = RequestMethod.GET)
    public ModelAndView buildingView(@PathVariable(value = "id") Long id) {
        ModelAndView mav = new ModelAndView("admin/building/view");
        BuildingDTO buildingDTO = buildingService.findById(id);
        mav.addObject("buildingView", buildingDTO);
        addObject(mav);
        return mav;
    }
    private void addObject(ModelAndView mav){
        mav.addObject("buildingTypes", DataUtils.getBuildingTypes());
        mav.addObject("districts", DataUtils.getDistricts());
    }

}
