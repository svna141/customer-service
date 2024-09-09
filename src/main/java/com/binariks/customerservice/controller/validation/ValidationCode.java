package com.binariks.customerservice.controller.validation;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ValidationCode {
    public static final String CUSTOMER_NAME_REQUIRED = "Customer`s name is required.";
    public static final String CUSTOMER_DATE_OF_BIRTH_REQUIRED = "Customer`s date of birth  is required.";
    public static final String CUSTOMER_NAME_LENGTH = "Customer`s name length should be between 0 and 100.";
    public static final String CUSTOMER_ADDRESS_LENGTH = "Customer`s address length should be not more than 200 symbols.";
}
