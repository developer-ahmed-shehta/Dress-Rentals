package com.weddingRental.service;

import com.weddingRental.model.Rental;
import com.weddingRental.repository.RentalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

// Service class to handle business logic for Rental entity
@Service
public class RentalService {

    private final RentalRepository rentalRepository;

    @Autowired // Spring will automatically inject the RentalRepository bean here
    public RentalService(RentalRepository rentalRepository) {
        this.rentalRepository = rentalRepository;
    }

    // Get all rentals
    public List<Rental> getAllRentals() {
        return rentalRepository.findAll();
    }

    // Get a rental by ID
    public Optional<Rental> getRentalById(Long id) {
        return rentalRepository.findById(id);
    }

    // Add a new rental
    public Rental addRental(Rental rental) {
        return rentalRepository.save(rental);
    }

    // Update an existing rental
    public Rental updateRental(Long id, Rental updatedRental) {
        if (rentalRepository.existsById(id)) {
            updatedRental.setId(id);  // Ensure the ID is not changed
            return rentalRepository.save(updatedRental);
        }
        return null;
    }

    // Delete a rental by ID
    public boolean deleteRental(Long id) {
        if (rentalRepository.existsById(id)) {
            rentalRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
