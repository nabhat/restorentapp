/**
 * 
 */
package com.proj.foodservice.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * ErrorResponse is a utility class used to standardize error messages returned by the application.
 * It encapsulates an error message and an associated error code to provide meaningful information
 * to the client when an error occurs in the system.
 */
public class ErrorResponse {
	Logger logger =LoggerFactory.getLogger(ErrorResponse.class);
	/**
     * The error message that describes what went wrong.
     */
    private String msg;
    /**
     * The error code representing the HTTP status or a custom error code.
     */
    private int code;  
    /**
     * Constructor to create an ErrorResponse object and log the error message and code.
     *
     * @param msg  The error message to be returned in the response.
     * @param code The HTTP status code or custom error code representing the error.
     */
    public ErrorResponse(String msg, int code) {
        this.msg = msg;
        this.code = code;
        logger.error("ErrorResponse created: Message - '{}', Code - {}", msg, code);  // Log the error response creation
    }
	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}
	/**
	 * @param msg the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}
	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}
    
    
}
