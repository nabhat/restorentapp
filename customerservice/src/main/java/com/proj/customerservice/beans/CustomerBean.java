package com.proj.customerservice.beans;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerBean {
    @NotNull(message = "First Name is required")
    @Size(max = 100, message = "Name must be at most 100 characters long.")
    private String FirstName;
    @NotNull(message = "Last Name is required")
    private String LastName;
    @Email
    @NotNull(message = "Email is required")
    private String Email;
    @NotNull(message = "Phone number is required.")
    @Positive(message = "Phone number must be a positive number.")
    @Min(1000000000L)
    @Max(9999999999L)
    private Long PhoneNumber;
    @NotNull(message = "Address is required")
    private String Address;
    @NotNull(message = "City is required")
    private String City;
    @NotNull(message = "State is required")
    private String State;
    @NotNull(message = "ZipCode is required")
    private String ZipCode;
    @NotNull(message = "Password is required.")
    @Size(min = 6, message = "Password must be at least 6 characters long.")
    private String Passcode;

}
