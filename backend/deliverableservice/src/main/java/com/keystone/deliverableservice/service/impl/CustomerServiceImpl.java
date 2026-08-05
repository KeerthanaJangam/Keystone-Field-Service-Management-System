package com.keystone.deliverableservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.keystone.deliverableservice.dto.response.CustomerResponse;
import com.keystone.deliverableservice.dto.request.CustomerRequest;
import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.exception.ResourceNotFoundException;
import com.keystone.deliverableservice.mapper.CustomerMapper;
import com.keystone.deliverableservice.repository.CustomerRepository;
import com.keystone.deliverableservice.service.CustomerService;

import lombok.RequiredArgsConstructor;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Transactional
public class CustomerServiceImpl  implements CustomerService {
	
	    private final CustomerRepository customerRepository;

	    private static final Logger logger =
	            LoggerFactory.getLogger(CustomerServiceImpl.class);
	    
	    @Override
	    public CustomerResponse createCustomer(CustomerRequest request) {
	    	//Log when the request starts
	    	  logger.info("Creating customer with company name: {}",request.getCompanyName());
	    	  
	        if (customerRepository.existsByCompanyName(request.getCompanyName())) {
	        	
	        	//log before throwing the exception
	        	 logger.warn("Customer already exists with company name: {}", request.getCompanyName());

	            throw new IllegalArgumentException("Customer already exists with company name: "
	                    + request.getCompanyName());
	        }

	        Customer customer = CustomerMapper.toEntity(request);

	        Customer savedCustomer = customerRepository.save(customer);
	        
	        // Log after the customer has been saved successfully
	        logger.info("Customer created successfully with ID: {}", savedCustomer.getId());

	        return CustomerMapper.toResponse(savedCustomer);
	    }

	    @Override
	    public CustomerResponse updateCustomer(Long id, CustomerRequest request) {
	    	
	    	logger.info("Updating customer with ID: {}", id);

	        Customer customer = customerRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Customer not found with id: " + id));

	        customerRepository.findByCompanyName(request.getCompanyName())
	                .ifPresent(existingCustomer -> {
	                    if (!existingCustomer.getId().equals(id)) {
	                        throw new IllegalArgumentException(
	                                "Another customer already exists with company name: "
	                                        + request.getCompanyName());
	                    }
	                });

	        CustomerMapper.updateEntity(customer, request);

	        Customer updatedCustomer = customerRepository.save(customer);
	        
	        logger.info("Customer updated successfully. ID: {}", id);

	        return CustomerMapper.toResponse(updatedCustomer);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public CustomerResponse getCustomerById(Long id) {

	    	logger.debug("Fetching customer with ID: {}", id);
	    	
	    	Customer customer = customerRepository.findById(id)
	    	        .orElseThrow(() -> {
	    	            logger.error("Customer not found with ID: {}", id);
	    	            return new ResourceNotFoundException("Customer not found with id: " + id);
	    	        });
//	        Customer customer = customerRepository.findById(id)
//	                .orElseThrow(() ->
//	                        new ResourceNotFoundException("Customer not found with id: " + id));

	        logger.info("Customer retrieved successfully. ID: {}", id);
	        
	        return CustomerMapper.toResponse(customer);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public List<CustomerResponse> getAllCustomers() {

	    	logger.debug("Fetching all customers");
	    	List<CustomerResponse> customers = customerRepository.findAll()
	                .stream()
	                .map(CustomerMapper::toResponse)
	                .collect(Collectors.toList());

	        logger.info("Retrieved {} customer(s) successfully.", customers.size());

	        return customers;
	    }

	    @Override
	    public void deleteCustomer(Long id) {

	    	logger.info("Deleting customer with ID: {}", id);
	    	
	        Customer customer = customerRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Customer not found with id: " + id));

	        logger.info("Customer deleted successfully. ID: {}", id);
	        
	        customerRepository.delete(customer);
	    }
	    
	}

