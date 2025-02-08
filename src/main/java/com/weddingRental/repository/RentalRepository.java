package com.weddingRental.repository;

import com.weddingRental.model.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

// Repository for Rental entity
@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {
    // You can add custom queries here, for example:
    // List<Rental> findByStatus(String status);
}
