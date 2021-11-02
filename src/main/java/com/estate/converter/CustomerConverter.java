package com.estate.converter;


import com.estate.dto.CustomerDTO;
import com.estate.entity.CustomerEntity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;


@Component
public class CustomerConverter {

    @Autowired
    private ModelMapper modelMapper;

    public CustomerDTO convertToDTO(CustomerEntity customerEntity) {
        CustomerDTO result = modelMapper.map(customerEntity, CustomerDTO.class);
        //result.setStatus(convertStatusDTO(customerEntity.getStatus()));
        result.setStaffId(customerEntity.getId());
        return result;
    }

    public CustomerEntity convertToEntity(CustomerDTO customerDTO) {
        CustomerEntity result = modelMapper.map(customerDTO, CustomerEntity.class);
        return result;
    }

    public static String convertStatusDTO(int status) {
        if (status == 1) {
            return "Đang xử lý";
        }
        return "Chưa xử lý ";
    }


}
