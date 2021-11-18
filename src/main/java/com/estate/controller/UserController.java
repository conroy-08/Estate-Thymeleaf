package com.estate.controller;

import com.estate.dto.UserDTO;
import com.estate.service.IRoleService;
import com.estate.service.IUserService;
import com.estate.utils.SecurityUtils;
import javassist.NotFoundException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;

@Controller(value = "userControllerOfAdmin")
public class UserController {


    @Autowired
    private IUserService userService;

    @Autowired
    private IRoleService roleService;



    @GetMapping("/user-list")
    public ModelAndView userList(@ModelAttribute("userModel") UserDTO model ){
        ModelAndView mav = new ModelAndView("admin/user/list");
        model.setTotalItems(userService.getTotalItems(model.getSearchValue()));
        model.setTotalPage((int) Math.ceil((double) model.getTotalItems() / model.getLimit()));
        List<UserDTO> users = userService.getUsers(model.getSearchValue(), PageRequest.of(model.getPage() - 1, model.getLimit()));
        model.setListResult(users);
        return  mav;
    }

    @GetMapping("/user-edit")
    public ModelAndView userEdit( @ModelAttribute("userEdit") UserDTO model ){
        ModelAndView mav = new ModelAndView("admin/user/edit");
        mav.addObject("Role",roleService.getRoles());
        return  mav;
    }


    @GetMapping("/user-edit-{id}")
    public ModelAndView updateUser(@PathVariable("id") Long id) throws NotFoundException {
        ModelAndView mav = new ModelAndView("admin/user/edit");
        UserDTO model = userService.findUserById(id);
        mav.addObject("Role",roleService.getRoles());
        mav.addObject("userEdit", model);
        return mav;
    }


    @GetMapping("/user-profile-{username}")
    public ModelAndView userProfile(@PathVariable("username") String userName) throws NotFoundException {
        ModelAndView mav = new ModelAndView("admin/user/profile");
        UserDTO userProfile = userService.findByUserName(userName);
        mav.addObject("userProfile",userProfile);
        return mav;
    }

    @GetMapping("/user-profile-password")
    public ModelAndView updatePassword() throws NotFoundException {
        ModelAndView mav = new ModelAndView("admin/user/password");
        UserDTO model = userService.findByUserName(SecurityUtils.getPrincipal().getUsername());
        mav.addObject("userPassword", model);
        return mav;
    }


}
