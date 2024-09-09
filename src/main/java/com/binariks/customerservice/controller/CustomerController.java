package com.binariks.customerservice.controller;

import com.binariks.customerservice.exception.CustomerNotFoundException;
import com.binariks.customerservice.model.CustomerRequestDTO;
import com.binariks.customerservice.model.CustomerResponseDTO;
import com.binariks.customerservice.model.CustomerRevisionResponseDTO;
import com.binariks.customerservice.service.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

import static com.binariks.customerservice.controller.RestConstants.API_PATH;
import static com.binariks.customerservice.controller.RestConstants.CUSTOMERS_PATH;
import static com.binariks.customerservice.controller.RestConstants.HISTORY_PATH;
import static com.binariks.customerservice.controller.RestConstants.ID_PATH;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(API_PATH + CUSTOMERS_PATH)
@Tag(name = "Customer Service", description = "Service to manage customers")
public class CustomerController {

    private final CustomerService customerService;

    @Operation(summary = "Create Customer")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<CustomerResponseDTO> createCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.ok(customerService.createCustomer(customerRequestDTO));
    }

    @Operation(summary = "Fetch Customer by id")
    @GetMapping(value = ID_PATH)
    public ResponseEntity<CustomerResponseDTO> getCustomer(@PathVariable("id") Long id) throws CustomerNotFoundException {
        return ResponseEntity.ok(customerService.getCustomer(id));
    }

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> searchCustomers(@RequestParam(name = "pageable", required = false) @SortDefault(sort = "name",
            direction = Sort.Direction.ASC) Pageable pageable) {
        return ResponseEntity.ok(customerService.getCustomers(pageable));
    }

    @Operation(summary = "Update Customer by id")
    @PatchMapping(value = ID_PATH)
    public ResponseEntity<CustomerResponseDTO> updateCustomer(@PathVariable("id") Long id, @RequestBody @Valid CustomerRequestDTO customerRequestDTO) throws CustomerNotFoundException {
        return ResponseEntity.ok(customerService.updateCustomer(id, customerRequestDTO));
    }

    @Operation(summary = "Delete Customer by id")
    @DeleteMapping(value = ID_PATH)
    public void deleteCustomer(@PathVariable("id") Long id) {
        customerService.deleteCustomer(id);
    }

    @Operation(summary = "Get Customer`s revision history by id")
    @GetMapping(value = ID_PATH + HISTORY_PATH)
    public ResponseEntity<List<CustomerRevisionResponseDTO>> getCustomerHistoryById(@PathVariable("id") Long id,
                                                                                    @RequestParam(name = "pageable", required = false) Pageable pageable) throws CustomerNotFoundException {
        return ResponseEntity.ok(customerService.getCustomerHistoryById(id, pageable));
    }
}
