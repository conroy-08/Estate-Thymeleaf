package com.estate.service.impl;

import com.estate.converter.TransactionConverter;
import com.estate.dto.TransactionDTO;
import com.estate.entity.CustomerEntity;
import com.estate.entity.TransactionEntity;
import com.estate.repository.CustomerRepository;
import com.estate.repository.TransactionRepository;
import com.estate.service.ITransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
public class TransactionService implements ITransactionService {

    @Autowired
    private TransactionRepository transactionRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private TransactionConverter transactionConverter;

    @Override
    @Transactional
    public void save(TransactionDTO transactionDTO) {
        TransactionEntity entity = transactionConverter.convertToEntity(transactionDTO);
        CustomerEntity customerEntity = customerRepository.findById(transactionDTO.getCustomerId()).get();
        entity.setCustomer(customerEntity);
        transactionRepository.save(entity);
    }
}
