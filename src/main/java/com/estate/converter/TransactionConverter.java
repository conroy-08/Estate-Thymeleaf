package com.estate.converter;


import com.estate.dto.TransactionDTO;
import com.estate.entity.TransactionEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TransactionConverter {

    @Autowired
    private ModelMapper modelMapper;

    public TransactionDTO convertToDTO(TransactionEntity transactionEntity) {
        TransactionDTO result = modelMapper.map(transactionEntity, TransactionDTO.class);
        return result;
    }

    public TransactionEntity convertToEntity(TransactionDTO transactionDTO) {
        TransactionEntity result = modelMapper.map(transactionDTO, TransactionEntity.class);
        return result;
    }
}
