package com.estate.controller;
import com.estate.dto.CustomerDTO;
import com.estate.service.ICustomerService;
import com.estate.service.IUserService;
import com.estate.utils.DataUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;



@Controller(value = "customerControllerOfAdmin")
public class CustomerController {


    private final IUserService userService;
    private final ICustomerService customerService;

    @Autowired
    public CustomerController(IUserService userService, ICustomerService customerService) {
        this.userService = userService;
        this.customerService = customerService;

    }

    @GetMapping("/customer-list")
    public String customerList(@ModelAttribute("customerSearch") CustomerDTO customerDTO , Model model) {
        customerDTO.setTotalItems(customerService.count(customerDTO));
        customerDTO.setTotalPage((int) Math.ceil((double) customerDTO.getTotalItems() / customerDTO.getLimit()));
        model.addAttribute("staffs", userService.getStaffs());
        model.addAttribute("customerList",customerService.findByCondition(customerDTO, PageRequest.of(customerDTO.getPage() - 1, customerDTO.getLimit())));
        return "admin/customer/list";
    }

    @GetMapping("/customer-edit")
    public String customerAdd(@ModelAttribute CustomerDTO customerDTO , Model model) {
        model.addAttribute("customerEdit", customerDTO);
        model.addAttribute("transactions", DataUtils.getTransactions());
        return "admin/customer/edit";
    }

    @GetMapping("/customer-edit-{id}")
    public String customerEdit(@PathVariable(value = "id") Long id , Model model) {
        CustomerDTO customer = customerService.findOneById(id);
        model.addAttribute("customerEdit", customer);
        model.addAttribute("transactions", DataUtils.getTransactions());
        return "admin/customer/edit";
    }



}
