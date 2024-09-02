/**
 * 
 */
package com.proj.customerservice.exception;

/**
 * EmailException is a custom exception class that represents errors related to email processing in the application.
 * 
 * This exception extends the standard Java Exception class, allowing it to be used as a checked exception
 * within the application. It is intended to be thrown when an email is already present in database.
 * 
 * The exception takes a message as a parameter, which provides detailed information about the specific
 * cause of the error. This message can be logged or presented to the user to inform them about the problem.
 */
public class EmailException extends Exception {
	/**
     * Constructs a new EmailException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
	public EmailException(String message) {
        super(message); //Passes the error message to the superclass (Exception)
    }
}
