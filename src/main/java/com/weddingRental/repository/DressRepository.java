package com.weddingRental.repository;

import com.weddingRental.model.Dress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DressRepository extends JpaRepository<Dress , Long> {
    // You can add custom queries here, for example:
    // Optional<Dress> findByName(String name);
}