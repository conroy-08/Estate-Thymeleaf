package com.estate.service.impl;

import com.estate.builder.CustomerSearchBuilder;
import com.estate.constant.SystemConstant;
import com.estate.converter.CustomerConverter;
import com.estate.dto.CustomerDTO;
import com.estate.dto.request.AssignmentRequest;
import com.estate.entity.CustomerEntity;
import com.estate.repository.CustomerRepository;
import com.estate.repository.UserRepository;
import com.estate.service.ICustomerService;
import com.estate.utils.SecurityUtils;
import javassist.NotFoundException;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService implements ICustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerConverter customerConverter;

    @Autowired
    private UserRepository userRepository;


    @Override
    public List<CustomerDTO> findByCondition(CustomerDTO customerDTO ,  Pageable pageable) {
        CustomerSearchBuilder builder = initCustomerBuilder(customerDTO);
        List<CustomerEntity> customerEntities = customerRepository.findByCondition(builder,pageable);
        List<CustomerDTO> customerDTOS = new ArrayList<>();
        customerEntities.forEach(item -> {
                    CustomerDTO dto = customerConverter.convertToDTO(item);
                    List<String> fullNames = new ArrayList<>();
                    item.getStaffs().forEach(i -> fullNames.add(i.getFullName()));
                    dto.setStaffName(StringUtils.join(fullNames, ","));
                    customerDTOS.add(dto);
                }
        );
        return customerDTOS;
    }

    @Override
    public CustomerDTO findOneById(Long id) {
        CustomerDTO customerDTO = customerConverter.convertToDTO(customerRepository.findById(id).get());
        return customerDTO;
    }

    @Override
    public CustomerDTO save(Long customerId, CustomerDTO dto) throws NotFoundException {
        CustomerEntity convertedCustomer = null;
        if (customerId != null && customerId > 0) {
            CustomerEntity updateCustomer = Optional.ofNullable(customerRepository.findById(customerId).get())
                    .orElseThrow(() -> new NotFoundException("Customer not found !"));
            convertedCustomer = customerConverter.convertToEntity(dto);
            convertedCustomer.setStaffs(updateCustomer.getStaffs());
        }else{
            convertedCustomer = customerConverter.convertToEntity(dto);
        }
        return customerConverter.convertToDTO(customerRepository.save(convertedCustomer));
    }

    @Override
    @Transactional
    public void assignmentCustomer(AssignmentRequest request) throws Exception {
        Long customerId = request.getId();
        CustomerEntity customerEntity = Optional.ofNullable(customerRepository.findById(customerId).get())
                .orElseThrow(() -> new NotFoundException("Customer not found !"));
        Long count = userRepository.countByIdIn(request.getUserIds());
        if (request.getUserIds().size() != count) {
            throw new Exception("Something wrong !");
        } else {
            customerEntity.setStaffs(userRepository.findAllById(request.getUserIds()));
            customerRepository.save(customerEntity);
        }

    }

    @Override
    @Transactional
    public void deleteCustomer(List<Long> ids) throws NotFoundException {
        long count = customerRepository.countByIdIn(ids);
        if (count != ids.size()) {
            throw new NotFoundException("Customer not found !");
        }
        customerRepository.deleteByIdIn(ids);
    }

    @Override
    public int count(CustomerDTO customerDTO) {
        CustomerSearchBuilder builder = initCustomerBuilder(customerDTO);
        return customerRepository.count(builder).intValue();
    }

    private CustomerSearchBuilder initCustomerBuilder(CustomerDTO customerDTO) {
        Long staffId;
        if (SecurityUtils.getAuthorities().contains(SystemConstant.STAFF_ROLE)) {
            staffId = SecurityUtils.getPrincipal().getId();
        } else {
            staffId = customerDTO.getStaffId();
        }
        CustomerSearchBuilder customerSearchBuilder = new CustomerSearchBuilder.Builder()
                .setFullName(customerDTO.getFullName())
                .setPhoneNumber(customerDTO.getPhone())
                .setEmail(customerDTO.getEmail())
                .setStaffId(staffId).build();

        return customerSearchBuilder;
    }
}
