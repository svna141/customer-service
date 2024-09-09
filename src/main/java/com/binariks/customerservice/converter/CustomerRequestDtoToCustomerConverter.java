package com.binariks.customerservice.converter;

import com.binariks.customerservice.model.CustomerRequestDTO;
import com.binariks.customerservice.model.GenderEnum;
import com.binariks.customerservice.repository.entity.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerRequestDtoToCustomerConverter {

    public static Customer convert(CustomerRequestDTO dto) {
        return Customer.builder()
                .name(dto.getName())
                .dateOfBirth(Timestamp.valueOf(dto.getDateOfBirth().atStartOfDay()))
                .address(dto.getAddress())
                .gender(GenderEnum.valueOf(dto.getGender()))
                .build();
    }
}
