package com.proj.customerservice.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.customerservice.entity.Customer;
import com.proj.customerservice.exception.EmailException;
import com.proj.customerservice.exception.MenuException;
import com.proj.customerservice.exception.PhoneException;
import com.proj.customerservice.repository.CustomerRepository;

import jakarta.transaction.Transactional;

/**
 * Service class that provides business logic related to customer operations.
 * This class interacts with the repository layer to perform CRUD operations 
 * on customer data and applies business rules for handling customers.
 * 
 * Annotations:
 * - @Service: Indicates that this class is a service component in the Spring context.
 *   It is used to hold the business logic of the application.
 * - @Transactional: Indicates that the methods within this class are transactional.
 *   This ensures that all operations within a method are executed within a transaction context.
 *   If any operation fails, the transaction is rolled back to maintain data integrity.
 */

@Service
@Transactional
public class CustomerService {

	// Repository instance to interact with the database for customer operations
    @Autowired
    CustomerRepository customerRepository;
    
    // Logger instance for logging information and errors
    Logger logger =LoggerFactory.getLogger(CustomerService.class);
    
    /**
     * Adds a new customer to the system.
     *
     * @param customer The Customer object to be added.
     * @return The saved Customer object after successful registration.
     * @throws EmailException If a customer with the same email already exists.
     * @throws Exception If any other error occurs during the process.
     */
    public Customer addService(Customer customer) throws MenuException ,EmailException,PhoneException,Exception{
    	logger.info("Attempting to save the customer: {}", customer);
    	// Check if a customer with the same email already exists
         Customer cust=customerRepository.findByEmail(customer.getEmail());
    	if(cust!=null) {
    		logger.error("Email already exists. Can't register new customer with email: {}", customer.getEmail());
    		throw new EmailException("Email already exists");  
	} 	
	Customer cust2=customerRepository.findByPhoneNumber(customer.getPhoneNumber());
    	if(cust2!=null) {
    		logger.error("Phone Number already exists. Can't register new customer with Phone Number: {}", customer.getPhoneNumber());
    		throw new PhoneException("Phone number already exists");
    	}
    	logger.info("Saving the new customer: {}", customer);
        // Save the new customer to the database
        return customerRepository.save(customer);
    }
    
    /**
     * Finds a customer by their unique ID.
     *
     * @param customerId The unique ID of the customer to be retrieved.
     * @return The Customer object if found, or null if no customer is found with the given ID.
     */
    public Customer findById(Integer customerId) {
    	logger.info("Finding the customer by ID: {}", customerId);
        // Retrieve the customer from the repository using the customer ID
        return customerRepository.findByCustomerId(customerId);
    }

    
}

