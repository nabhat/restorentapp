package com.proj.customerservice.entity;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a Customer entity in the system.
 * This entity is mapped to the "customer" table in the database.
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Customer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer customerId;// Unique identifier for the customer
    
    private String firstName;   // First name of the customer
    private String lastName;    // Last name of the customer
    private String email;       // Email address of the customer
    private String phoneNumber; // Phone number of the customer
    private String address;     // Physical address of the customer
    private String city;        // City where the customer resides
    private String state;       // State where the customer resides
    private String zipCode;     // Zip code of the customer's address
    private String passcode;    // Password or passcode for the customer

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "customer")
    @JsonIgnore
    private List<Orders> order;// List of orders associated with the customer
}