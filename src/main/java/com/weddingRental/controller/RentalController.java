package com.weddingRental.controller;

import com.weddingRental.model.Rental;
import com.weddingRental.service.RentalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

// Controller to handle HTTP requests for Rental entity
@RestController
@RequestMapping("/api/rentals") // Base URL for all rental-related API endpoints
public class RentalController {

    private final RentalService rentalService;

    @Autowired // Spring will automatically inject the RentalService bean here
    public RentalController(RentalService rentalService) {
        this.rentalService = rentalService;
    }

    // Get all rentals
    @GetMapping
    public ResponseEntity<List<Rental>> getAllRentals() {
        List<Rental> rentals = rentalService.getAllRentals();
        return new ResponseEntity<>(rentals, HttpStatus.OK);
    }

    // Get a rental by ID
    @GetMapping("/{id}")
    public ResponseEntity<Rental> getRentalById(@PathVariable Long id) {
        Optional<Rental> rental = rentalService.getRentalById(id);
        return rental.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Add a new rental
    @PostMapping
    public ResponseEntity<Rental> addRental(@RequestBody Rental rental) {
        Rental savedRental = rentalService.addRental(rental);
        return new ResponseEntity<>(savedRental, HttpStatus.CREATED);
    }

    // Update an existing rental
    @PutMapping("/{id}")
    public ResponseEntity<Rental> updateRental(@PathVariable Long id, @RequestBody Rental rental) {
        Rental updatedRental = rentalService.updateRental(id, rental);
        return updatedRental != null ? new ResponseEntity<>(updatedRental, HttpStatus.OK) : ResponseEntity.notFound().build();
    }

    // Delete a rental by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteRental(@PathVariable Long id) {
        boolean deleted = rentalService.deleteRental(id);
        return deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build();
    }
}
