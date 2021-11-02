package com.estate.service;


import com.estate.dto.CustomerDTO;
import com.estate.dto.request.AssignmentRequest;
import javassist.NotFoundException;

import java.util.List;

public interface ICustomerService {
    List<CustomerDTO> findByCondition(CustomerDTO customerDTO);
    CustomerDTO findOneById(Long id);
    CustomerDTO save(Long customerId , CustomerDTO dto) throws NotFoundException;
    void assignmentCustomer(AssignmentRequest request) throws Exception ;
    void deleteCustomer(List<Long> ids) throws NotFoundException;

}
