package com.binariks.customerservice.exception;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ErrorMessage {
    public static final String CUSTOMER_NOT_FOUND = "Customer with id %s was not found.";
    public static final String REQUEST_VALIDATION_FAILED = "Request validation falied.";

    public static final String NOT_UNIQUE_VIOLATION = "Unique index or primary key violation.";

}
