package com.proj.customerservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.proj.customerservice.entity.Customer;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
 
    /**
     * @param customerId
     * @return
     */
    Customer findByCustomerId(Integer customerId);
    /**
     * @param email
     * @return
     */
    // JPQL query to find a Customer by email
    @Query("SELECT c FROM Customer c WHERE c.email = :email")
    Customer findByEmail(@Param("email") String email);
	/**
	 * @param phoneNumber
	 * @return
	 */
	Customer findByPhoneNumber(String phoneNumber);
}