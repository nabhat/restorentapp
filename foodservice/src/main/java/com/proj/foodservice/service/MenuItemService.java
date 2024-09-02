/**
 * 
 */
package com.proj.foodservice.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proj.foodservice.entity.MenuItem;
import com.proj.foodservice.exception.MenuException;
import com.proj.foodservice.repository.MenuItemRepository;

/**
 * Service class that provides business logic related to menu items (dishes).
 * This class interacts with the repository layer to perform CRUD operations and
 * applies business rules for handling menu items.
 * 
 * Annotations:
 * - @Service: Indicates that this class is a service component in the Spring context.
 *   It is used to hold the business logic of the application.
 */

@Service
public class MenuItemService {
	
	// Repository instance to interact with the database
    @Autowired
    private MenuItemRepository repository;
    
    // Logger instance for logging information and errors
	private static final Logger logger = LoggerFactory.getLogger(MenuItemService.class);
	
	/**
     * Retrieves a list of menu items based on the dish category.
     *
     * @param dishType The category of dishes to retrieve.
     * @return List of MenuItem objects that belong to the specified category.
     * @throws MenuException If no dishes are found for the given category.
     */
    public List<MenuItem> getMenus(String dishType) throws MenuException {
    	logger.info("Attempting to get list of dishes for category: {}", dishType);

        // Fetches the list of menu items from the repository based on the dish category
        List<MenuItem> value = repository.findByDishCategory(dishType);
        logger.info("Fetched list of dishes: {}", value);
        
        // Checks if the list is empty or null and throws an exception if so
        if(value.isEmpty()||value==null){
        	logger.error("No dishes available for category: {}", dishType);
            throw new MenuException("No Dish avialbe");
        }
        
        logger.info("Returning list of dishes for category {}: {}", dishType, value);
        return value;
    }
    /**
     * Retrieves a menu item by its ID.
     *
     * @param id The ID of the menu item to retrieve.
     * @return The MenuItem object with the specified ID.
     * @throws MenuException If no menu item is found with the given ID.
     */
    public MenuItem getMenuById(int id) throws MenuException {
    	logger.info("Attempting to get dish by ID: {}", id);
    	// Fetches the menu item by its ID from the repository, throws exception if not found
        return repository.findById(id).orElseThrow(() -> new MenuException("Dish not found"));
    }
}
