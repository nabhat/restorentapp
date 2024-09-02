package com.proj.customerservice.exception;

public class TimeException extends Exception{
    /**
     * Constructs a new EmailException with the specified detail message.
     *
     * @param message the detail message that explains the reason for the exception
     */
    public TimeException(String message) {
        super(message); //Passes the error message to the superclass (Exception)
    }

}
