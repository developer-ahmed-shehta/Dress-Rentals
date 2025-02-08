package com.weddingRental.model;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "customer")
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    @Id // Marks this field as the primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto-generates the ID value
    private Long id;

    private String name; // Customer's name

    @Column(unique = true, nullable = false) // Email is unique and cannot be null
    private String email; // Customer's email address

    @Column(unique = true, nullable = false) // Phone number is unique and cannot be null
    private String phone; // Customer's phone number
}
