package com.keystone.deliverableservice.service;

import java.util.List;

import com.keystone.deliverableservice.dto.request.CustomerRequest;
import com.keystone.deliverableservice.dto.response.CustomerResponse;

public interface CustomerService 
{
 CustomerResponse createCustomer(CustomerRequest request);
 CustomerResponse updateCustomer(Long id, CustomerRequest request);
 CustomerResponse getCustomerById(Long id);
 List<CustomerResponse> getAllCustomers();
 
 void deleteCustomer(Long id);
}
