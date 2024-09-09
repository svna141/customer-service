package com.binariks.customerservice.converter;

import com.binariks.customerservice.model.CustomerResponseDTO;
import com.binariks.customerservice.repository.entity.Customer;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.Period;

import static com.binariks.customerservice.util.ConverterUtil.calculateAge;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class CustomerToCustomerResponseDtoConverter {
    public static CustomerResponseDTO convert(Customer customer) {
        LocalDate dateOfBirth = customer.getDateOfBirth().toLocalDateTime().toLocalDate();
        return CustomerResponseDTO.builder()
                .id(customer.getId())
                .name(customer.getName())
                .dateOfBirth(dateOfBirth)
                .age(calculateAge(dateOfBirth))
                .address(customer.getAddress())
                .gender(customer.getGender())
                .build();
    }
}
