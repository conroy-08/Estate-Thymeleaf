package com.estate.controller;

import com.estate.dto.CustomerDTO;
import com.estate.service.ICustomerService;
import com.estate.service.IUserService;
import com.estate.utils.DataUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

@Controller(value = "customerControllerOfAdmin")
public class CustomerController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICustomerService customerService;

    @GetMapping("/customer-list")
    public ModelAndView customerList(@ModelAttribute("customerSearch") CustomerDTO customerDTO) {
        ModelAndView mav = new ModelAndView("admin/customer/list");
        customerDTO.setListResult(customerService.findByCondition(customerDTO));
        mav.addObject("staffs", userService.getStaffs());
        return mav;
    }

    @GetMapping("/customer-edit")
    public  ModelAndView customerEdit(){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        CustomerDTO customerDTO = new CustomerDTO();
        mav.addObject("customerEdit", customerDTO);
        mav.addObject("transactions", DataUtils.getTransactions());
        return mav;
    }

    @GetMapping("/customer-edit-{id}")
    public  ModelAndView customerEdit(@PathVariable(value = "id") Long id ){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        CustomerDTO customerDTO = customerService.findOneById(id);
        mav.addObject("customerEdit", customerDTO);
        mav.addObject("transactions", DataUtils.getTransactions());
        return mav;
    }

}
