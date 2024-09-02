package com.proj.customerservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.proj.customerservice.entity.Customer;
import com.proj.customerservice.entity.Orders;

public interface OrderRepository  extends JpaRepository<Orders,Long>{    
	Orders findByOrderId(Integer id);
    void deleteByOrderId(Integer id);
    List<Orders> findByCustomer(Customer customer);
}
