package com.estate.service;


import com.estate.dto.CustomerDTO;
import com.estate.dto.request.AssignmentRequest;
import javassist.NotFoundException;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ICustomerService {
    List<CustomerDTO> findByCondition(CustomerDTO customerDTO ,  Pageable pageable);
    CustomerDTO findOneById(Long id);
    CustomerDTO save(Long customerId , CustomerDTO dto) throws NotFoundException;
    void assignmentCustomer(AssignmentRequest request) throws Exception ;
    void deleteCustomer(List<Long> ids) throws NotFoundException;
    int count(CustomerDTO customerDTO);

}
