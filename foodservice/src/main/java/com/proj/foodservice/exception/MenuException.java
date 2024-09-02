/**
 * 
 */
package com.proj.foodservice.exception;

/**
 * MenuException is a custom exception class that represents errors related to the menu operations
 * in the application.
 * 
 * This exception extends the standard Java Exception class, making it a checked exception. It is 
 * designed to be thrown when issues related to menu management arise.
 * 
 * The exception takes a descriptive message as a parameter, which provides context about the specific
 * cause of the error. This message can be used for logging or to notify the user of the issue.
 */
public class MenuException extends Exception {
	/**
     * Constructs a new MenuException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public MenuException(String message) {
    	super(message);  // Passes the error message to the superclass (Exception)
    }
}

