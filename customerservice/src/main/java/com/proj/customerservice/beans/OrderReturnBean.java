/**
 * 
 */
package com.proj.customerservice.beans;

import lombok.Data;

/**
 * 
 */
@Data
public class OrderReturnBean {
	private Integer OrderId;
	private Integer dishId;
    private Integer customerId;
    private Integer status;
    private Integer quantity;
}
