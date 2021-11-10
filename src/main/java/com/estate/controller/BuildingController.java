package com.estate.controller;

import com.estate.constant.SystemConstant;
import com.estate.dto.BuildingDTO;
import com.estate.service.IBuildingService;
import com.estate.service.IUserService;
import com.estate.utils.DataUtils;
import com.estate.utils.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;


@Controller(value = "buildingControllerOfAdmin")
public class BuildingController {

    @Autowired
    private IBuildingService buildingService;

    @Autowired
    private IUserService userService;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("/building-list")
    public ModelAndView buildingList(@ModelAttribute("modelSearch") BuildingDTO buildingDTO , HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/building/list");
        Pageable pageable = PageRequest.of(buildingDTO.getPage()- 1, buildingDTO.getLimit());
        buildingDTO.setTotalItems(buildingService.count(buildingDTO));
        buildingDTO.setTotalPage((int) Math.ceil((double) buildingDTO.getTotalItems() / buildingDTO.getLimit()));
        buildingDTO.setListResult(buildingService.findByCondition(buildingDTO, pageable));
        mav.addObject("staffs", userService.getStaffs());
        initMessageResponse(mav, request);
        addObject(mav);
        return mav;
    }

    @GetMapping("/building-edit")
    public ModelAndView buildingEdit(HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/building/edit" );
        BuildingDTO buildingDTO = new BuildingDTO();
        mav.addObject("buildingEdit", buildingDTO);
        initMessageResponse(mav, request);
        addObject(mav);
        return mav;
    }

    @GetMapping("/building-edit-{id}")
    public ModelAndView buildingEdit(@PathVariable(value = "id") Long id , HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/building/edit");
        BuildingDTO buildingDTO = buildingService.findById(id);
        mav.addObject("buildingEdit", buildingDTO);
        initMessageResponse(mav, request);
        addObject(mav);
        return mav;
    }

    @GetMapping("/building-view-{id}")
    public ModelAndView buildingView(@PathVariable(value = "id") Long id , HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/building/view");
        BuildingDTO buildingDTO = buildingService.findById(id);
        mav.addObject("buildingView", buildingDTO);
        initMessageResponse(mav, request);
        addObject(mav);
        return mav;
    }

    private void addObject(ModelAndView mav) {
        mav.addObject("buildingTypes", DataUtils.getBuildingTypes());
        mav.addObject("districts", DataUtils.getDistricts());
    }
    private  void initMessageResponse(ModelAndView mav , HttpServletRequest request){
        String message = request.getParameter("message");
        if (message != null && StringUtils.isNotEmpty(message)){
            Map<String , String> messageMap = messageUtil.getMessage(message);
            mav.addObject(SystemConstant.ALERT, messageMap.get(SystemConstant.ALERT));
            mav.addObject(SystemConstant.MESSAGE_RESPONSE, messageMap.get(SystemConstant.MESSAGE_RESPONSE));
        }
    }

}
