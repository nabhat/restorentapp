package com.proj.customerservice.beans;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

/**
 * 
 */

@Data
public class OrderBean {
    @NotNull
    private Integer dishId;
    @NotNull
    private Integer customerId;
    @NotNull(message = "Quantity Can't be zero")
    @Positive
    private Integer quantity;
}