package com.binariks.customerservice.model;

import com.binariks.customerservice.model.format.CustomerDateFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRevisionResponseDTO {
    private Long id;
    private Long revId;
    private String name;
    private int age;
    @CustomerDateFormat
    private LocalDate dateOfBirth;
    private String address;
    private GenderEnum gender;
    private LocalDateTime timestamp;
    private String revType;

}
