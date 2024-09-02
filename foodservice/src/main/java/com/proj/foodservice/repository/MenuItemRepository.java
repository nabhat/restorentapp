/**
 * 
 */
package com.proj.foodservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.proj.foodservice.entity.MenuItem;

/**
 * Repository interface for MenuItem entities.
 * This interface provides methods to perform CRUD operations on MenuItem entities
 * in the database using Spring Data JPA.
 * 
 * Inherits from JpaRepository to leverage Spring Data JPA's capabilities, such as:
 * - Standard CRUD operations (save, findById, findAll, deleteById, etc.)
 * - Paging and sorting functionality.
 * 
 * Annotations:
 * - @Repository: Indicates that this interface is a repository component in the Spring context.
 *   It is used to interact with the persistence layer and is a candidate for Spring's component scanning to register as a bean.
 */
@Repository
public interface MenuItemRepository extends JpaRepository<MenuItem, Integer> {
	/**
     * Retrieves a list of MenuItem entities that match the given dish category.
     *
     * @param dishType The category of dishes to search for.
     * @return A list of MenuItem objects that belong to the specified dish category.
     */
    List<MenuItem> findByDishCategory(String dishType);
                    
}

