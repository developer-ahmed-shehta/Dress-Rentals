package com.weddingRental.model;

import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

// Define the Rental entity and map it to the 'rental' table in the database
@Entity
@Table(name = "rental")
@Getter // Lombok annotation to generate getters
@Setter // Lombok annotation to generate setters
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
public class Rental {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID value
    private Long id;

    // Many-to-One relationship between Rental and Customer
    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false) // Foreign key to the 'customer' table
    private Customer customer;

    // Many-to-One relationship between Rental and Dress
    @ManyToOne
    @JoinColumn(name = "dress_id", nullable = false) // Foreign key to the 'dress' table
    private Dress dress;

    private LocalDate startDate; // Rental start date
    private LocalDate endDate; // Rental end date
    private double totalPrice; // Total price for the rental duration

    @Column(nullable = false) // Default value for rental status
    private String status = "Pending";  // Default status for the rental (e.g., 'Pending', 'Completed')
}
