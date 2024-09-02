package com.proj.customerservice.exception;

/**
 * PhoneException is a custom exception class that represents errors related to phone number processing in the application.
 * 
 * This exception extends the standard Java Exception class, allowing it to be used as a checked exception
 * within the application. It is intended to be thrown when an email is already present in database.
 * 
 * The exception takes a message as a parameter, which provides detailed information about the specific
 * cause of the error. This message can be logged or presented to the user to inform them about the problem.
 */
public class PhoneException extends Exception{

	/**
	 * @param message
	 */
	public PhoneException(String message) {
		super(message);
	}
	

}
