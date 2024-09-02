/**
 * 
 */
package com.proj.customerservice.DTO;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

/**
 * 
 */
@Data
public class OrdersDTO {
    @NotNull
    private Integer orderId;
    @NotNull
    private Integer dishId;
    @NotNull
    private Integer customerId;
    @NotNull
    private Integer quantity;
}
