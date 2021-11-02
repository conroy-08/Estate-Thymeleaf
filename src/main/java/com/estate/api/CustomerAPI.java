package com.estate.api;

import com.estate.dto.CustomerDTO;
import com.estate.dto.TransactionDTO;
import com.estate.dto.request.AssignmentRequest;
import com.estate.dto.response.ResponseDTO;
import com.estate.dto.response.StaffReponseDTO;
import com.estate.service.ICustomerService;
import com.estate.service.ITransactionService;
import com.estate.service.IUserService;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController(value = "customerApiOfAdmin")
@RequestMapping("/api/customer")
public class CustomerAPI {

    @Autowired
    private ICustomerService customerService;

    @Autowired
    private ITransactionService transactionService;

    @Autowired
    private IUserService userService;

    @GetMapping("/{customerId}/staffs")
    public ResponseEntity<ResponseDTO> loadStaff(@PathVariable("customerId") Long customerId) {
        try {
            ResponseDTO result = new ResponseDTO();
            List<StaffReponseDTO> staffs = userService.findAllStaff(null, customerId);
            result.setData(staffs);
            return ResponseEntity.status(HttpStatus.OK).body(result);
        } catch (Exception e) {
            throw e;
        }
    }

    @PostMapping("/assignment")
    public void assignmentBuilding(@RequestBody AssignmentRequest assignmentRequest) throws Exception {
        customerService.assignmentCustomer(assignmentRequest);
    }

    @PostMapping()
    public ResponseEntity<CustomerDTO> createCustomer(@RequestBody CustomerDTO customerDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customerDTO.getId(), customerDTO));
    }

    @PutMapping()
    public ResponseEntity<CustomerDTO> updateCustomer(@RequestBody CustomerDTO customerDTO) throws NotFoundException {
        return ResponseEntity.status(HttpStatus.OK).body(customerService.save(customerDTO.getId(), customerDTO));

    }

    @DeleteMapping
    public void deleteCustomers(@RequestBody List<Long> ids) throws NotFoundException {
        customerService.deleteCustomer(ids);
    }

    @PostMapping("/transaction")
    public void Transaction(@RequestBody TransactionDTO transactionDTO) {
        transactionService.save(transactionDTO);
    }

}
