/**
 * 
 */
package com.proj.foodservice.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.proj.foodservice.entity.MenuItem;
import com.proj.foodservice.exception.MenuException;
import com.proj.foodservice.service.MenuItemService;
import com.proj.foodservice.util.ErrorResponse;

/**
 * This class is a REST controller that handles HTTP requests related to menu items (dishes).
 * It provides end points to perform operations such as retrieving a dish by its ID
 * or fetching a list of dishes by their category.
 *
 * Annotations:
 * - @RestController: Indicates that this class is a RESTful controller where 
 *   each method returns a domain object instead of a view. 
 *   It combines @Controller and @ResponseBody, meaning the returned objects 
 *   are automatically serialized into JSON or XML format and sent as the HTTP response.
 * 
 * - @RequestMapping("/dish"): Specifies the base URL path for which this controller will handle requests.
 *   All end points in this controller will be prefixed with "/dish".
 *   For example, "/dish/{dishId}" or "/dish/category/{dishCategory}".
 */

@RestController
@RequestMapping("/dish")
public class MenuItemController {
	
	 private static final Logger logger = LoggerFactory.getLogger(MenuItemController.class);


    @Autowired
    private MenuItemService service;
    /**
     * Retrieves a menu item by its ID.
     *
     * @param dishId The ID of the dish to retrieve.
     * @return ResponseEntity containing the menu item if found, otherwise an error response.
     */
    @GetMapping("/{dishId}")
    public ResponseEntity<?> getMenuById(@PathVariable int dishId) {
    	logger.info("Received request to get dish by ID: {}", dishId);
        try {
        	// Attempt to fetch the menu item from the service layer
            MenuItem menuItem = service.getMenuById(dishId);
            logger.info("Successfully retrieved dish with ID {}: {}", dishId, menuItem);
            return new ResponseEntity<>(menuItem, HttpStatus.OK);
        } catch (MenuException e) {
        	// Log the error if the dish ID is not found or any other issue occurs
            logger.error("Error retrieving dish with ID {}: {}", dishId, e.getMessage());
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 404), HttpStatus.NOT_FOUND);
        }
    }
    /**
     * Retrieves a list of menu items by their category.
     *
     * @param dishCategory The category of dishes to retrieve.
     * @return ResponseEntity containing the list of menu items if found, otherwise an error response.
     */
    @GetMapping("/category/{dishCategory}")
    public ResponseEntity<?> getMenusByDishType(@PathVariable String dishCategory) {
    	logger.info("Received request to get list of dishes by category: {}", dishCategory);
        try{
        	// Attempt to fetch the list of menu items from the service layer based on category
            List<MenuItem> menuItems = service.getMenus(dishCategory);
            logger.info("Successfully retrieved dishes for category {}: {}", dishCategory, menuItems);
            return new ResponseEntity<>(menuItems, HttpStatus.OK);
        }
        catch (MenuException e) {
        	// Log the error if no dishes are found for the specified category or any other issue occurs
            logger.error("Error retrieving dishes for category {}: {}", dishCategory, e.getMessage());
            return new ResponseEntity<>(new ErrorResponse(e.getMessage(), 404), HttpStatus.NOT_FOUND);
        
        }
    
    }
}