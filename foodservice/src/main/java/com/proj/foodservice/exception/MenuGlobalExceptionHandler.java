/**
 * 
 */
package com.proj.foodservice.exception;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.proj.foodservice.util.ErrorResponse;
/**
 * 
 */

@ControllerAdvice
public class MenuGlobalExceptionHandler {
	// Logger instance for logging exceptions and error messages
    Logger logger = LoggerFactory.getLogger(MenuGlobalExceptionHandler.class);
    /**
     * Handles exceptions of type MenuException.
     * 
     * This method captures MenuException thrown anywhere in the application and
     * returns a ResponseEntity containing an ErrorResponse object with a 404 NOT FOUND status.
     * 
     * @param ex the MenuException thrown in the application
     * @return ResponseEntity<ErrorResponse> - Response containing the error message and HTTP 404 status
     */
    @ExceptionHandler(MenuException.class)
    public ResponseEntity<ErrorResponse> handleMenuException(MenuException ex) {
    	 // Log the MenuException message
        logger.error("MenuException occurred: {}", ex.getMessage());
        
        // Create an ErrorResponse with the exception message and 404 status code
        ErrorResponse error = new ErrorResponse(ex.getMessage(), 404);
        
        // Return a ResponseEntity containing the ErrorResponse and HTTP status NOT_FOUND
        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }
    
    /**
     * Handles exceptions of type MethodArgumentNotValidException.
     * 
     * This method captures validation errors that occur when request data does not meet the 
     * validation criteria specified in the request body (e.g., annotations like @NotNull or @Size).
     * It collects the validation errors and returns them in a map, with each field name as the key 
     * and the validation error message as the value.
     * 
     * @param ex the MethodArgumentNotValidException thrown during validation
     * @return ResponseEntity<Map<String, String>> - Response containing validation errors and HTTP 400 status
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
    	// Log the validation exception message
        logger.error("Validation error occurred: {}", ex.getMessage());

        // Create a map to hold the validation error messages
        Map<String, String> errors = new HashMap<>();
        // Iterate through the validation errors and collect field names and error messages
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();// Extract the field name that failed validation
            String errorMessage = error.getDefaultMessage();// Extract the default validation error message
            errors.put(fieldName, errorMessage);// Add the field name and error message to the map
        });

        // Return a ResponseEntity containing the map of validation errors and HTTP status BAD_REQUEST
        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}

