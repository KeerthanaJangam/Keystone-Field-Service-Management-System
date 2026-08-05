package com.keystone.deliverableservice.mapper;

import com.keystone.deliverableservice.dto.request.CustomerRequest;
import com.keystone.deliverableservice.dto.response.CustomerResponse;
import com.keystone.deliverableservice.entity.Customer;

public class CustomerMapper 
{
  private CustomerMapper() {
	  
  }
  public static Customer toEntity(CustomerRequest request) {
	  
	  Customer customer = new Customer();
	  customer.setCompanyName(request.getCompanyName());
      customer.setContactPerson(request.getContactPerson());
      customer.setEmail(request.getEmail());
      customer.setPhone(request.getPhone());
      customer.setAddress(request.getAddress());

      return customer;
  }
  public static CustomerResponse toResponse(Customer customer) {

      return CustomerResponse.builder()
              .id(customer.getId())
              .companyName(customer.getCompanyName())
              .contactPerson(customer.getContactPerson())
              .email(customer.getEmail())
              .phone(customer.getPhone())
              .address(customer.getAddress())
              .build();
  }
  public static void updateEntity(Customer customer,
          CustomerRequest request) {

	customer.setCompanyName(request.getCompanyName());
	customer.setContactPerson(request.getContactPerson());
	customer.setEmail(request.getEmail());
	customer.setPhone(request.getPhone());
	customer.setAddress(request.getAddress());
	}
}
