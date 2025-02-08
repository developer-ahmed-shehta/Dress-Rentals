package com.weddingRental.model;

import jakarta.persistence.*;
import lombok.*;

// Define the Dress entity and map it to the 'dress' table in the database
@Entity
@Table(name = "dress")
@Getter // Lombok annotation to generate getters
@Setter // Lombok annotation to generate setters
@NoArgsConstructor // Lombok annotation to generate a no-args constructor
@AllArgsConstructor // Lombok annotation to generate an all-args constructor
public class Dress {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID value
    private Long id;

    private String name; // The name of the dress
    private String description; // A short description of the dress
    private String size; // Size of the dress (e.g., Small, Medium, Large)
    private double pricePerDay; // Price for renting the dress per day
    private boolean availability; // Indicates if the dress is available for rental

    @Column(name = "photo_path") // Maps to the 'photo_path' column in the database
    private String photoPath;  // The file path to the image of the dress
}
