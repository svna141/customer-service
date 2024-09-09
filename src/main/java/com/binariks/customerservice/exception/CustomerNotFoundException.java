package com.binariks.customerservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import static com.binariks.customerservice.exception.ErrorMessage.CUSTOMER_NOT_FOUND;

@ResponseStatus(value = HttpStatus.BAD_REQUEST, reason = CUSTOMER_NOT_FOUND)
public class CustomerNotFoundException extends Exception {
    public CustomerNotFoundException(String message) {
        super(message);
    }
}
