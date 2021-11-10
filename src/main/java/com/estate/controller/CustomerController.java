package com.estate.controller;

import com.estate.constant.SystemConstant;
import com.estate.dto.CustomerDTO;
import com.estate.service.ICustomerService;
import com.estate.service.IUserService;
import com.estate.utils.DataUtils;
import com.estate.utils.MessageUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller(value = "customerControllerOfAdmin")
public class CustomerController {

    @Autowired
    private IUserService userService;

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private MessageUtils messageUtil;

    @GetMapping("/customer-list")
    public ModelAndView customerList(@ModelAttribute("customerSearch") CustomerDTO customerDTO ,HttpServletRequest request) {
        ModelAndView mav = new ModelAndView("admin/customer/list");
        customerDTO.setTotalItems(customerService.count(customerDTO));
        customerDTO.setTotalPage((int) Math.ceil((double) customerDTO.getTotalItems() / customerDTO.getLimit()));
        customerDTO.setListResult(customerService.findByCondition(customerDTO, PageRequest.of(customerDTO.getPage()-1, customerDTO.getLimit())));
        mav.addObject("staffs", userService.getStaffs());
        initMessageResponse(mav, request);
        return mav;
    }

    @GetMapping("/customer-edit")
    public  ModelAndView customerEdit(HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        CustomerDTO customerDTO = new CustomerDTO();
        mav.addObject("customerEdit", customerDTO);
        mav.addObject("transactions", DataUtils.getTransactions());
        initMessageResponse(mav, request);
        return mav;
    }

    @GetMapping("/customer-edit-{id}")
    public  ModelAndView customerEdit(@PathVariable(value = "id") Long id , HttpServletRequest request){
        ModelAndView mav = new ModelAndView("admin/customer/edit");
        CustomerDTO customerDTO = customerService.findOneById(id);
        mav.addObject("customerEdit", customerDTO);
        mav.addObject("transactions", DataUtils.getTransactions());
        initMessageResponse(mav, request);
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
