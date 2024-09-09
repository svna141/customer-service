package com.binariks.customerservice.model;

import com.binariks.customerservice.model.format.CustomerDateFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerResponseDTO {
    private Long id;
    private String name;
    private int age;
    @CustomerDateFormat
    private LocalDate dateOfBirth;
    private String address;
    private GenderEnum gender;

}
