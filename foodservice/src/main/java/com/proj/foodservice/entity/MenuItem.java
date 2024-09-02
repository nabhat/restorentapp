package com.proj.foodservice.entity;


import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * The MenuItem class represents an entity for a menu item (dish) in a restaurant system.
 * It includes various fields that represent the properties of a menu item such as ID, category, name, price, and description.
 * 
 * Annotations:
 * - @Entity: Specifies that the class is an entity and is mapped to a database table.
 * - @Id: Indicates the primary key of the entity.
 * - @GeneratedValue: Specifies the generation strategy for the primary key.
 * - @NotNull: Ensures that the annotated field is not null and throws a validation error if it is.
 * - @Positive: Ensures that the annotated field is a positive number.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Menu")
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer dishId; // Unique identifier for the dish

    @NotNull(message = "Category must be given")
    private String dishCategory;// The category of the dish (e.g., Appetizer, Main Course, Dessert)
    
    @NotNull(message = "Name must be given")
    private String dishName;// The name of the dish

    @NotNull
    @Positive(message = "Price must be positive")
    private Integer unitPrice;// The unit price of the dish; must be a positive value
    
    @NotNull(message = "Descrption must be given")
    private String dishDesc; // Description of the dish
}
