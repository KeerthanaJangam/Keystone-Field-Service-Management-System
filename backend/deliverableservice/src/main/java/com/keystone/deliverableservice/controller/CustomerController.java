package com.keystone.deliverableservice.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.keystone.deliverableservice.dto.request.CustomerRequest;
import com.keystone.deliverableservice.dto.response.CustomerResponse;
import com.keystone.deliverableservice.service.CustomerService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("api/customers")
@RequiredArgsConstructor
public class CustomerController 
{
	@Autowired
   private  CustomerService customerService;
   
   @PostMapping
   @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
   public ResponseEntity<CustomerResponse> createCustomer(@Valid @RequestBody CustomerRequest request) {

       return new ResponseEntity<>(
               customerService.createCustomer(request),
               HttpStatus.CREATED
       );
   }
   
   @GetMapping
   @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
   public ResponseEntity<List<CustomerResponse>> getAllCustomers(){
	    return ResponseEntity.ok(customerService.getAllCustomers());
   }
   
   @GetMapping("/{id}")
   @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
   public ResponseEntity<CustomerResponse> getCustomer(@PathVariable Long id){
	   
	   return ResponseEntity.ok(customerService.getCustomerById(id));
   }
   
   @PutMapping("/{id}")
   @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
   public ResponseEntity<CustomerResponse> updateCustomer(@PathVariable Long id, @Valid @RequestBody CustomerRequest request){
	   
	   return ResponseEntity.ok(customerService.updateCustomer(id, request));
   }
   @DeleteMapping("/{id}")
   @PreAuthorize("hasAnyRole('ADMIN')")
   public ResponseEntity<Void> deleteCustomer(@PathVariable Long id){
	   customerService.deleteCustomer(id);
	   
	   return ResponseEntity.noContent().build();
   }
   
}
