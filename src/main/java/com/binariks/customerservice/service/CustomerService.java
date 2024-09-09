package com.binariks.customerservice.service;

import com.binariks.customerservice.exception.CustomerNotFoundException;
import com.binariks.customerservice.model.CustomerRequestDTO;
import com.binariks.customerservice.model.CustomerResponseDTO;
import com.binariks.customerservice.model.CustomerRevisionResponseDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface CustomerService {
    CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO);

    CustomerResponseDTO getCustomer(Long id) throws CustomerNotFoundException;

    List<CustomerResponseDTO> getCustomers(Pageable pageable);

    CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO) throws CustomerNotFoundException;

    void deleteCustomer(Long id);

    List<CustomerRevisionResponseDTO> getCustomerHistoryById(Long id, Pageable pageable) throws CustomerNotFoundException;
}
