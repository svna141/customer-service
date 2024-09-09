package com.binariks.customerservice.exception;

import com.binariks.customerservice.model.format.CustomerDateFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
@Getter
@Builder
@Setter
@Data
public class ErrorResponseDTO {
    private String requestUri;
    @CustomerDateFormat
    private LocalDate timestamp;
    private String errorMessage;
    private List<String> errors;
}
