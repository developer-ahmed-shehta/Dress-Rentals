package com.weddingRental.service;

import com.weddingRental.model.Dress;
import com.weddingRental.repository.DressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

// Service class to handle business logic for Dress entity
@Service
public class DressService {

    private final DressRepository dressRepository;

    @Autowired // Spring will automatically inject the DressRepository bean here
    public DressService(DressRepository dressRepository) {
        this.dressRepository = dressRepository;
    }

    // Get all dresses
    public Page<Dress> getAllDresses(Pageable pageable) {
        return dressRepository.findAll(pageable); // Use the Pageable in the repository method
    }

    // Get a dress by ID
    public Optional<Dress> getDressById(Long id) {
        return dressRepository.findById(id);
    }

    // Add a new dress
    public Dress addDress(Dress dress) {
        return dressRepository.save(dress);
    }

    // Delete a dress by ID
    public boolean deleteDress(Long id) {
        if (dressRepository.existsById(id)) {
            dressRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Update an existing dress
    public void saveDress(Dress dress) {
        if (dress.getId() == null) {
            // New dress: persist it
            dressRepository.save(dress);
        } else {
            // Existing dress: update it
            Dress existingDress = dressRepository.findById(dress.getId()).orElse(null);
            if (existingDress != null) {
                // Update the existingDress's properties with the values from the 'dress' parameter.
                existingDress.setName(dress.getName());
                existingDress.setDescription(dress.getDescription());
                existingDress.setPricePerDay(dress.getPricePerDay());
                existingDress.setAvailability(dress.isAvailability());
                existingDress.setPhotoPath(dress.getPhotoPath());
                dressRepository.save(existingDress);
            }
        }
    }
}
