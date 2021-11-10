package com.estate.controller;


;
import com.estate.constant.SystemConstant;
import com.estate.dto.UserDTO;
import com.estate.service.IRoleService;
import com.estate.service.IUserService;
import com.estate.utils.MessageUtils;
import com.estate.utils.SecurityUtils;
import javassist.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;


import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Controller(value = "userControllerOfAdmin")
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;

    @Autowired
    private MessageUtils messageUtil;


    @GetMapping("/user-list")
    public ModelAndView userList(@ModelAttribute("userModel") UserDTO model , HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/user/list");
        model.setTotalItems(userService.getTotalItems(model.getSearchValue()));
        model.setTotalPage((int) Math.ceil((double) model.getTotalItems() / model.getLimit()));
        List<UserDTO> users = userService.getUsers(model.getSearchValue(), PageRequest.of(model.getPage() - 1, model.getLimit()));
        model.setListResult(users);
        initMessageResponse(mav, request);
        return  mav;
    }

    @GetMapping("/user-edit")
    public ModelAndView userEdit(@ModelAttribute("userEdit") UserDTO model , HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/user/edit");
        initMessageResponse(mav, request);
        mav.addObject("Role",roleService.getRoles());
        return  mav;
    }


    @GetMapping("/user-edit-{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id, HttpServletRequest request) throws NotFoundException {
        ModelAndView mav = new ModelAndView("admin/user/edit");
        UserDTO model = userService.findUserById(id);
        initMessageResponse(mav, request);
        mav.addObject("Role",roleService.getRoles());
        mav.addObject("userEdit", model);
        return mav;
    }


    @GetMapping("/user-profile-{username}")
    public ModelAndView userProfile(@PathVariable("username") String userName, HttpServletRequest request) throws NotFoundException {
        ModelAndView mav = new ModelAndView("admin/user/profile");
        UserDTO userProfile = userService.findByUserName(userName);
        initMessageResponse(mav, request);
        mav.addObject("userProfile",userProfile);
        return mav;
    }

    @GetMapping("/user-profile-password")
    public ModelAndView updatePassword(HttpServletRequest request) throws NotFoundException {
        ModelAndView mav = new ModelAndView("admin/user/password");
        UserDTO model = userService.findByUserName(SecurityUtils.getPrincipal().getUsername());
        initMessageResponse(mav, request);
        mav.addObject(SystemConstant.MODEL, model);
        return mav;
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
