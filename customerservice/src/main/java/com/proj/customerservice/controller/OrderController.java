package com.proj.customerservice.controller;

import com.proj.customerservice.exception.TimeException;
import com.proj.customerservice.util.ErrorResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.proj.customerservice.beans.OrderBean;
import com.proj.customerservice.service.CustomerService;
import com.proj.customerservice.service.OrderService;

import jakarta.validation.Valid;

/**
 * OrderController handles HTTP requests related to customer orders.
 * 
 * This class acts as a REST controller in the Spring MVC framework and exposes various
 * endpoints for managing orders such as placing new orders, deleting existing orders,
 * and retrieving order details for a specific customer.
 * 
 * The controller is mapped to the "/ordertemp" base URL, meaning all endpoints within
 * this controller will be prefixed with "/ordertemp". The endpoints provided by this controller
 * typically respond to standard HTTP methods like POST for creating orders, DELETE for removing orders,
 * and GET for retrieving order information.
 * 
 * Key endpoints include:
 * - POST /ordertemp/order: Places a new order.
 * - DELETE /ordertemp/order/{id}: Deletes an order based on its ID.
 * 
 * This controller relies on OrderService to perform business logic and interacts with
 * other services like CustomerService and the database through service layers. Each method
 * returns a ResponseEntity, ensuring proper HTTP status codes and structured responses.
 * 
 * The controller is annotated with @RestController, making it a Spring MVC controller 
 * that returns JSON responses by default, and it leverages automatic request mapping 
 * and response conversion based on the content type.
 */

@RestController
//@RequestMapping("/ordertemp")
public class OrderController {
     @Autowired
    CustomerService customerService;// Injecting CustomerService to handle customer-related actions

    @Autowired
    OrderService orderService; // Injecting OrderService to handle order-related actions
    @Autowired
    RestTemplate restTemplate;// Injecting RestTemplate for inter-service communication


    Logger logger =LoggerFactory.getLogger(OrderController.class); // Logger instance
    
    
    /**
     * Place a new order and write it to the database.
     * 
     * @param ordersDTO - the order details received as a JSON object in the request body.
     * @return ResponseEntity - HTTP response with the status of the operation.
     */
    @PostMapping("/order")
    //Place order and Write it to the DB*/
    public ResponseEntity<?> placeOrder(@Valid @RequestBody OrderBean ordersDTO) {
    	logger.info("Received request to place a new order: {}", ordersDTO);
    	try {
    		 logger.info("Order successfully placed: {}", ordersDTO);
    		 ResponseEntity<?> response = orderService.addOrder(ordersDTO);
    	return response;
    	}catch (Exception e) {
            logger.error("Failed to place the order: {} {}", ordersDTO, e);
            return new ResponseEntity<>("Failed to place order", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
    /**
     * Delete an existing order based on the order ID.
     * 
     * @param id - the ID of the order to be deleted.
     * @return ResponseEntity - HTTP response with the status of the operation.
     */
   @PutMapping("order/{id}")
   //Delete the Order Based on ID
   public ResponseEntity<?> delOrder(@PathVariable Integer id){
	   logger.info("Received request to delete order with id: {}", id);
	   
        try {
        	ResponseEntity<?> response = new ResponseEntity<>(orderService.removeOrder(id), HttpStatus.ACCEPTED);
        	logger.info("Order with id {} successfully cancelled", id);
        	return response;
		} catch (TimeException e) {
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 403), HttpStatus.FORBIDDEN);
        }
        catch (Exception e) {
			 logger.error("Failed to delete the order with id: {}", id, e);
			 return new ResponseEntity<>("Failed to delete order", HttpStatus.INTERNAL_SERVER_ERROR);
		}
   }


}