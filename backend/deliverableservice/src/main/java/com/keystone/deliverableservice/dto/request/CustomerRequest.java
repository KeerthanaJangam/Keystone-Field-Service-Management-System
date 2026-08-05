package com.keystone.deliverableservice.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CustomerRequest 
{
@NotBlank(message = "Company name is required")
 private String companyName;

@NotBlank(message = "Contact person is required")
 private String contactPerson;

@Email(message = "Invalid email")
 private String email;

@NotBlank(message = "Phone number is required")
 private String phone;

@NotBlank(message = "Address is required")
 private String address;
 
}
