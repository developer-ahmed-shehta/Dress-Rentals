package com.weddingRental.repository;

import com.weddingRental.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for Customer entity
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    // Custom queries can go here, for example:
    // Optional<Customer> findByEmail(String email);
}
