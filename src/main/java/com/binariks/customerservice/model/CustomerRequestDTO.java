package com.binariks.customerservice.model;

import com.binariks.customerservice.controller.validation.ValueOfEnum;
import com.binariks.customerservice.model.format.CustomerDateFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

import static com.binariks.customerservice.controller.validation.ValidationCode.CUSTOMER_ADDRESS_LENGTH;
import static com.binariks.customerservice.controller.validation.ValidationCode.CUSTOMER_DATE_OF_BIRTH_REQUIRED;
import static com.binariks.customerservice.controller.validation.ValidationCode.CUSTOMER_NAME_LENGTH;
import static com.binariks.customerservice.controller.validation.ValidationCode.CUSTOMER_NAME_REQUIRED;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CustomerRequestDTO {
    @NotBlank(message = CUSTOMER_NAME_REQUIRED)
    @Length(min = 2, max = 100, message = CUSTOMER_NAME_LENGTH)
    private String name;
    @CustomerDateFormat
    @NotNull(message = CUSTOMER_DATE_OF_BIRTH_REQUIRED)
    private LocalDate dateOfBirth;
    @Length(max = 200, message = CUSTOMER_ADDRESS_LENGTH)
    private String address;
    @ValueOfEnum(enumClass = GenderEnum.class)
    private String gender;

}
