/**
 * 
 */
package com.proj.customerservice.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.proj.customerservice.DTO.OrdersDTO;
import com.proj.customerservice.beans.CustomerBean;
import com.proj.customerservice.beans.OrderReturnBean;
import com.proj.customerservice.entity.Customer;
import com.proj.customerservice.entity.Orders;
import com.proj.customerservice.exception.EmailException;
import com.proj.customerservice.exception.MenuException;
import com.proj.customerservice.service.CustomerService;
import com.proj.customerservice.service.OrderService;
import com.proj.customerservice.util.ErrorResponse;

import jakarta.validation.Valid;

/**
 * 
 */

@RestController
public class CustomerController {
	@Autowired
	CustomerService customerService;
	@Autowired
	OrderService orderService;
	@Autowired
	RestTemplate restTemplate;

	
	
	 Logger logger = LoggerFactory.getLogger(CustomerController.class);

	@PostMapping("/customer")
	// Add Customer to the DB
	public ResponseEntity<?> savEntity(@Valid @RequestBody CustomerBean customerBean) {
		logger.info("--------------------");
		logger.info("TO register the customer Bean recived",customerBean);
		try {
			
			Customer customer = new Customer();
			customer.setFirstName(customerBean.getFirstName());
			customer.setLastName(customerBean.getLastName());
			customer.setAddress(customerBean.getAddress());
			customer.setEmail(customerBean.getEmail());
			customer.setPhoneNumber(customerBean.getPhoneNumber() + "");
			customer.setCity(customerBean.getCity());
			customer.setState(customerBean.getState());
			customer.setZipCode(customerBean.getZipCode());
			customer.setPasscode(customerBean.getPasscode());
			Customer cust = customerService.addService(customer);
			logger.info("Saving customer {}",cust);
			return new ResponseEntity<>(cust, HttpStatus.OK);

		} catch (MenuException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
		}catch (EmailException e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 403), HttpStatus.FORBIDDEN);
		} catch (Exception e) {
			logger.error(e.getMessage());
			return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 400), HttpStatus.BAD_REQUEST);
		}
	}	
	@GetMapping("/customer/{customerId}")
	// Get list of order placed by a Customer
	public ResponseEntity<?> getOrdersByCustomer(@PathVariable Integer customerId) {
		
		logger.info("Getting the list of order placed by customer");
		Customer customer = new Customer();

		customer.setCustomerId(customerId);
		logger.info("Checling if the customer is present with id {}",customerId);
		Customer customer2 = customerService.findById(customerId);
		if (customer2 == null) {
			logger.error("Customer not found with ID {}",customerId);
			return new ResponseEntity<>(new ErrorResponse("Customer not found", 404),HttpStatus.NOT_FOUND);
		}
		List<Orders> order1 = orderService.getOrdersByCustomer(customer);
		logger.info("List of order of customer with id {} is {}",customerId,order1);
		List<OrderReturnBean> orderDTOs = order1.stream().map(order -> {
			OrderReturnBean dto = new OrderReturnBean();
			dto.setOrderId(order.getOrderId());
			dto.setDishId(order.getDishId());
			dto.setQuantity(order.getQuantity());
			dto.setCustomerId(order.getCustomer().getCustomerId());
			dto.setStatus(order.getStatus());
			return dto;
		}).collect(Collectors.toList());
			logger.info("Returning the list of order {}",orderDTOs);
		return ResponseEntity.ok(orderDTOs);
	}
}