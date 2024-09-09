package com.binariks.customerservice.service;

import com.binariks.customerservice.converter.CustomerRequestDtoToCustomerConverter;
import com.binariks.customerservice.converter.CustomerRevisionToCustomerRevisionResponseDtoConverter;
import com.binariks.customerservice.converter.CustomerToCustomerResponseDtoConverter;
import com.binariks.customerservice.exception.CustomerNotFoundException;
import com.binariks.customerservice.model.CustomerRequestDTO;
import com.binariks.customerservice.model.CustomerResponseDTO;
import com.binariks.customerservice.model.CustomerRevisionResponseDTO;
import com.binariks.customerservice.model.GenderEnum;
import com.binariks.customerservice.repository.CustomerRepository;
import com.binariks.customerservice.repository.entity.Customer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.history.Revision;
import org.springframework.data.history.RevisionSort;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

import static com.binariks.customerservice.exception.ErrorMessage.CUSTOMER_NOT_FOUND;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl implements CustomerService {
    private final CustomerRepository customerRepository;
    private static final int REVISION_PAGE_SIZE = 100;
    private static final int DEFAULT_PAGE_SIZE = 10;

    @Override
    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        Customer customer = CustomerRequestDtoToCustomerConverter.convert(customerRequestDTO);
        Customer customerCreated = customerRepository.save(customer);

        return CustomerToCustomerResponseDtoConverter.convert(customerCreated);
    }

    @Override
    public CustomerResponseDTO getCustomer(Long id) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, id)));

        return CustomerToCustomerResponseDtoConverter.convert(customer);
    }

    @Override
    public List<CustomerResponseDTO> getCustomers(Pageable pageRequest) {
        pageRequest = validateAndGetPageRequest(pageRequest, DEFAULT_PAGE_SIZE);
        List<CustomerResponseDTO> response = null;
        try {
            Page<Customer> customers = customerRepository.findAll(pageRequest);
            response = customers.stream()
                    .map(CustomerToCustomerResponseDtoConverter::convert)
                    .toList();
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return response;
    }

    @Override
    public CustomerResponseDTO updateCustomer(Long id, CustomerRequestDTO customerRequestDTO) throws CustomerNotFoundException {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, id)));
        updateCustomerFromDto(customer, customerRequestDTO);
        Customer customerUpdated = customerRepository.save(customer);

        return CustomerToCustomerResponseDtoConverter.convert(customerUpdated);
    }

    @Override
    public void deleteCustomer(Long id) {
        if (customerRepository.existsById(id)) {
            customerRepository.deleteById(id);
        }
    }

    @Override
    public List<CustomerRevisionResponseDTO> getCustomerHistoryById(Long id, Pageable pageRequest) throws CustomerNotFoundException {
        validateCustomerExists(id);
        pageRequest = validateAndGetPageRequest(pageRequest, REVISION_PAGE_SIZE);

        List<CustomerRevisionResponseDTO> customerRevisionList = null;
        try {
            Pageable pageable = PageRequest.of(pageRequest.getPageNumber(), pageRequest.getPageSize(), RevisionSort.desc());
            Page<Revision<Long, Customer>> customerRevisions = customerRepository.findRevisions(id, pageable);

            customerRevisionList = customerRevisions.stream()
                    .map(CustomerRevisionToCustomerRevisionResponseDtoConverter::convert)
                    .toList();
        } catch (DataAccessException ex) {
            log.error(ex.getMessage());
        }
        return customerRevisionList;
    }

    private void validateCustomerExists(Long id) throws CustomerNotFoundException {
        if (!customerRepository.existsById(id)) {
            throw new CustomerNotFoundException(String.format(CUSTOMER_NOT_FOUND, id));
        }
    }

    private Pageable validateAndGetPageRequest(Pageable pageRequest, int pageSize) {
        if (pageRequest == null) {
            pageRequest = PageRequest.of(0, pageSize);
        }
        return pageRequest;
    }

    private void updateCustomerFromDto(Customer customer, CustomerRequestDTO dto) {
        if (isFieldValueNotNull(dto.getName())) {
            customer.setName(dto.getName());
        }
        if (isFieldValueNotNull(dto.getDateOfBirth())) {
            customer.setDateOfBirth(Timestamp.valueOf(dto.getDateOfBirth().atStartOfDay()));
        }
        if (isFieldValueNotNull(dto.getAddress())) {
            customer.setAddress(dto.getAddress());
        }
        if (isFieldValueNotNull(dto.getGender())) {
            customer.setGender(GenderEnum.valueOf(dto.getGender()));
        }
    }

    private boolean isFieldValueNotNull(Object value) {
        return value != null;
    }

}
