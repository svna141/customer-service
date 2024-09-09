package com.binariks.customerservice.controller;

import com.binariks.customerservice.exception.CustomerNotFoundException;
import com.binariks.customerservice.exception.ErrorResponseDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDate;
import java.util.List;

import static com.binariks.customerservice.exception.ErrorMessage.CUSTOMER_NOT_FOUND;
import static com.binariks.customerservice.exception.ErrorMessage.NOT_UNIQUE_VIOLATION;
import static com.binariks.customerservice.exception.ErrorMessage.REQUEST_VALIDATION_FAILED;
import static org.springframework.http.HttpStatus.CONFLICT;

@Slf4j
@ControllerAdvice
public class GlobalExceptionAdvice extends ResponseEntityExceptionHandler {
    @ResponseBody
    @ExceptionHandler(CustomerNotFoundException.class)
    public ResponseEntity<ErrorResponseDTO> handleCustomerNotFoundException(CustomerNotFoundException ex, WebRequest request) {
        String userRequest = request.getDescription(false);
        String id = StringUtils.substringAfterLast(userRequest, "/");

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(String.format(CUSTOMER_NOT_FOUND, id))
                .timestamp(LocalDate.now())
                .requestUri(userRequest)
                .build();

        log.error(errorResponseDTO.toString(), ex);

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDTO);
    }

    @ResponseBody
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDTO> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {

        String userRequest = request.getDescription(false);

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(NOT_UNIQUE_VIOLATION)
                .timestamp(LocalDate.now())
                .requestUri(userRequest)
                .build();

        log.error(errorResponseDTO.toString(), ex);

        return ResponseEntity.status(CONFLICT).body(errorResponseDTO);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
        String userRequest = request.getDescription(false);

        List<String> errors = ex.getBindingResult().getFieldErrors().stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();

        ErrorResponseDTO errorResponseDTO = ErrorResponseDTO.builder()
                .errorMessage(REQUEST_VALIDATION_FAILED)
                .timestamp(LocalDate.now())
                .requestUri(userRequest)
                .errors(errors)
                .build();

        log.error(errorResponseDTO.toString(), ex);

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDTO);
    }

}
