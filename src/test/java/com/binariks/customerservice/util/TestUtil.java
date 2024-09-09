package com.binariks.customerservice.util;

import com.binariks.customerservice.model.CustomerRequestDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.time.LocalDate;

public class TestUtil {
    public static final String name = "Jack All";
    public static final String name2 = "Jane Tier";
    public static final String address = "London";
    public static final String address2 = "Dublin";
    public static final String maleGender = "M";
    public static final String femaleGender = "F";
    public static final LocalDate dateOfBirth = LocalDate.parse("1999-01-01");
    public static final LocalDate dateOfBirth2 = LocalDate.parse("1999-10-01");

    public static CustomerRequestDTO getDefaultCustomerRequest() {
        return CustomerRequestDTO.builder()
                .name(name)
                .dateOfBirth(dateOfBirth)
                .address(address)
                .gender(maleGender)
                .build();

    }

    public static CustomerRequestDTO getUpdatedCustomerRequest() {
        return CustomerRequestDTO.builder()
                .name(name2)
                .dateOfBirth(dateOfBirth2)
                .address(address2)
                .gender(femaleGender)
                .build();

    }

    public static String asJsonString(final Object obj) {
        try {
            return new ObjectMapper().writeValueAsString(obj);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
