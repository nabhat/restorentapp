package com.proj.customerservice.service;

import java.time.LocalDateTime;
import java.util.List;

import com.proj.customerservice.exception.EmailException;
import com.proj.customerservice.exception.TimeException;
import com.proj.customerservice.util.TimeDifferenceChecker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.proj.customerservice.beans.OrderBean;
import com.proj.customerservice.beans.OrderReturnBean;
import com.proj.customerservice.entity.Customer;
import com.proj.customerservice.entity.Orders;
import com.proj.customerservice.repository.OrderRepository;
import com.proj.customerservice.util.ErrorResponse;

import jakarta.transaction.Transactional;

/**
 * OrderService is responsible for managing the business logic related to customer orders.
 * This service provides functionalities such as placing new orders, validating dish and 
 * customer information, removing existing orders, and retrieving orders associated with 
 * a specific customer.
 * 
 * The service handles interactions with external services (such as validating dishes
 * through a RestTemplate call) and database operations (via OrderRepository) within
 * transactional boundaries. It also ensures that all necessary validations are performed 
 * before any data is persisted or deleted.
 * 
 * The key methods include:
 * - addOrder: Places a new order, validates the dish and customer, and saves the order to the database.
 * - removeOrder: Deletes an order by its ID.
 * - getOrdersByCustomer: Retrieves a list of orders associated with a given customer.
 * 
 * Logging is integrated into the service to track the workflow and capture any errors
 * that may occur during operations.
 * 
 * This class is annotated with @Service and @Transactional to ensure that the service
 * layer is properly managed by Spring and that database transactions are automatically
 * handled for all methods.
 */
@Service
@Transactional // Marks this service as transactional, so that all operations occur within a transaction context.
public class OrderService {

    @Autowired
    OrderRepository orderRepository;// Repository for database operations related to Orders.
    
    @Autowired
    RestTemplate restTemplate; // RestTemplate for making HTTP calls to other services.
    @Autowired
    CustomerService customerService; // Service to handle customer-related logic.

    Logger logger =LoggerFactory.getLogger(OrderService.class);// Logger for logging information and errors.
    
    /**
     * Add a new order. Validates if the dish and customer exist before saving the order.
     * 
     * @param ordersDTO - The details of the order to be placed.
     * @return ResponseEntity - HTTP response with the result of the operation.
     */
    public ResponseEntity<?> addOrder(OrderBean ordersDTO) {
    	 Orders order = new Orders();// Create a new order instance.
         order.setDishId(ordersDTO.getDishId());// Set the dish ID for the order.
         order.setQuantity(ordersDTO.getQuantity());// Set the quantity for the order.
         
         try {
        	// Validate if the dish is present by calling the Food Service.
        	 logger.info("Checking if the dish with id {} is valid", ordersDTO.getDishId());
        	 String url = "http://localhost:9091/client1/dish/" + ordersDTO.getDishId(); // Construct the URL to check dish validity.
        	
        	 // Making a GET request to the Food Service.
        	 ResponseEntity<String> response = restTemplate.getForEntity(url, String.class);
        	 logger.info("Dish with id {} is valid. Response from service: {}", ordersDTO.getDishId(), response);

        	 try {
        		// Validate if the customer exists in the system.
        		 
                 logger.info("Checking if the customer with id {} is valid", ordersDTO.getCustomerId());
                 Customer customer = customerService.findById(ordersDTO.getCustomerId());
        		 if (customer == null) {
        			 logger.error("Customer with id {} not found.", ordersDTO.getCustomerId());
        			 return new ResponseEntity<>(new ErrorResponse("Customer not found", 404),HttpStatus.NOT_FOUND); // Or handle as needed
        		 }
        		// Customer is valid, set customer information in the order.
        		 order.setCustomer(customer);
        		 logger.info("Customer with id {} is valid. Customer info: {}", ordersDTO.getCustomerId(), customer);
        		try {
        			// Save the order to the database and return the response.
        			LocalDateTime now = LocalDateTime.now();
        			order.setOrdertime(now);
        			order.setStatus(1);
           		 Orders savedOrder = orderRepository.save(order);
           	     // Log the order creation
           	     savedOrder.logOrderCreation();
                    logger.info("Order saved successfully: {}", savedOrder);
           		 return new ResponseEntity<>(savedOrder, HttpStatus.CREATED);
        		}catch(Exception e) {
        		Orders savedOrder=new Orders();
        		savedOrder.logOrderError(e.getMessage());
        		return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 406), HttpStatus.NOT_ACCEPTABLE);
        		}
        	 } catch (Exception e) {
        		 // Catch and log exceptions related to customer validation or other issues.
        		 logger.error("An error occurred while validating customer or saving the order: {}", e.getMessage());
        	 	 return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 404), HttpStatus.NOT_FOUND);
        	 }
         
         } catch (Exception e) {
        	// Catch and log exceptions related to dish validation.
        	logger.error("An error occurred while validating the dish: {}", e.getMessage());
            return new ResponseEntity<>(new ErrorResponse("Dish not found", 404), HttpStatus.NOT_FOUND);
        }    
    }
   
    /**
     * Remove an order from the system by order ID.
     * 
     * @param id - The ID of the order to be removed.
     * @return Integer - The ID of the deleted order.
     */
    public OrderReturnBean removeOrder(Integer id) throws  TimeException{
    	logger.info("Removing order with id: {}", id);
    	
    	Orders order1= orderRepository.findByOrderId(id);
		if(TimeDifferenceChecker.isDifference10Minutes(order1.getOrdertime(),LocalDateTime.now())){
			order1.setStatus(0);
		}else{
			throw new TimeException("Order can't be cancelled 10min after placing");
		}

    	orderRepository.save(order1);
    	Orders order2=orderRepository.findByOrderId(id);
        //orderRepository.deleteByOrderId(id);// Delete the order from the repository based on the order ID.
    	OrderReturnBean orb=new OrderReturnBean();
    	orb.setCustomerId(order2.getCustomer().getCustomerId());
    	orb.setDishId(order2.getDishId());
    	orb.setOrderId(order2.getOrderId());
    	orb.setQuantity(order2.getQuantity());
    	orb.setStatus(order2.getStatus());
    	logger.info("Order {} cancelled successfully",orb);
        return orb; // Return the ID of the deleted order.
        
    }
    
    /**
     * Get all orders for a specific customer.
     * 
     * @param customer - The customer whose orders are to be fetched.
     * @return List<Orders> - The list of orders for the customer.
     */
    public List<Orders> getOrdersByCustomer(Customer customer) {
    	logger.info("Fetching all orders for customer: {}", customer.getCustomerId());
    	List<Orders> orders =orderRepository.findByCustomer(customer);// Fetch orders from the repository.
    	logger.info("Found {} orders for customer: {}", orders.size(), customer.getCustomerId());
        return orders;
    }
}