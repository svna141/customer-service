package com.binariks.customerservice.repository;

import com.binariks.customerservice.repository.entity.Customer;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.repository.history.RevisionRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long>,
        RevisionRepository<Customer, Long, Long> {

    @Override
    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    Optional<Customer> findById(Long id);
}

