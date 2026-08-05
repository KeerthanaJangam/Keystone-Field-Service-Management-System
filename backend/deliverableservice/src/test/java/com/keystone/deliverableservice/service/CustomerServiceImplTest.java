package com.keystone.deliverableservice.service;

import com.keystone.deliverableservice.dto.request.CustomerRequest;
import com.keystone.deliverableservice.dto.response.CustomerResponse;
import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.exception.ResourceNotFoundException;
import com.keystone.deliverableservice.repository.CustomerRepository;
import com.keystone.deliverableservice.service.impl.CustomerServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceImplTest {

	@Mock
	private CustomerRepository customerRepository;
	
	@InjectMocks
	private CustomerServiceImpl customerService;
	
	@Test
	void createCustomer_ShouldCreateCustomerSuccessfully() {

	    // Arrange
	    CustomerRequest request = new CustomerRequest();
	    request.setCompanyName("ABC Technologies");
	    request.setContactPerson("Rahul Sharma");
	    request.setEmail("rahul@abc.com");
	    request.setPhone("9876543210");
	    request.setAddress("Hyderabad");

	    when(customerRepository.existsByCompanyName("ABC Technologies"))
	            .thenReturn(false);

	    Customer savedCustomer = Customer.builder()
	            .id(1L)
	            .companyName("ABC Technologies")
	            .contactPerson("Rahul Sharma")
	            .email("rahul@abc.com")
	            .phone("9876543210")
	            .address("Hyderabad")
	            .build();

	    when(customerRepository.save(any(Customer.class)))
	            .thenReturn(savedCustomer);

	    // Act
	    CustomerResponse response = customerService.createCustomer(request);

	    // Assert
	    assertNotNull(response);
	    assertEquals(1L, response.getId());
	    assertEquals("ABC Technologies", response.getCompanyName());
	    assertEquals("Rahul Sharma", response.getContactPerson());
	    assertEquals("rahul@abc.com", response.getEmail());
	    assertEquals("9876543210", response.getPhone());
	    assertEquals("Hyderabad", response.getAddress());

	    verify(customerRepository, times(1))
	            .existsByCompanyName("ABC Technologies");

	    verify(customerRepository, times(1))
	            .save(any(Customer.class));
	}
	@Test
	void getCustomer_ShouldReturnCustomer_WhenCustomerExists() {

	    // Arrange
	    Customer customer = Customer.builder()
	            .id(1L)
	            .companyName("ABC Technologies")
	            .contactPerson("Rahul Sharma")
	            .email("rahul@abc.com")
	            .phone("9876543210")
	            .address("Hyderabad")
	            .build();

	    when(customerRepository.findById(1L))
	            .thenReturn(Optional.of(customer));

	    // Act
	    CustomerResponse response = customerService.getCustomerById(1L);

	    // Assert
	    assertNotNull(response);
	    assertEquals(1L, response.getId());
	    assertEquals("ABC Technologies", response.getCompanyName());
	    assertEquals("Rahul Sharma", response.getContactPerson());
	    assertEquals("rahul@abc.com", response.getEmail());
	    assertEquals("9876543210", response.getPhone());
	    assertEquals("Hyderabad", response.getAddress());

	    verify(customerRepository, times(1))
	            .findById(1L);
	}
	@Test
	void getCustomer_ShouldThrowException_WhenCustomerNotFound() {

	    // Arrange
	    when(customerRepository.findById(1L))
	            .thenReturn(Optional.empty());

	    // Act & Assert
	    ResourceNotFoundException exception =
	            assertThrows(
	                    ResourceNotFoundException.class,
	                    () -> customerService.getCustomerById(1L)
	            );

	    assertEquals(
	            "Customer not found with id: 1",
	            exception.getMessage()
	    );

	    verify(customerRepository, times(1))
	            .findById(1L);
	}
	@Test
	void getAllCustomers_ShouldReturnCustomerList() {

	    // Arrange
	    Customer customer1 = Customer.builder()
	            .id(1L)
	            .companyName("ABC Technologies")
	            .contactPerson("Rahul Sharma")
	            .email("rahul@abc.com")
	            .phone("9876543210")
	            .address("Hyderabad")
	            .build();

	    Customer customer2 = Customer.builder()
	            .id(2L)
	            .companyName("XYZ Solutions")
	            .contactPerson("Priya")
	            .email("priya@xyz.com")
	            .phone("9876543211")
	            .address("Bangalore")
	            .build();

	    when(customerRepository.findAll())
	            .thenReturn(List.of(customer1, customer2));

	    // Act
	    List<CustomerResponse> response = customerService.getAllCustomers();

	    // Assert
	    assertNotNull(response);
	    assertEquals(2, response.size());

	    assertEquals("ABC Technologies", response.get(0).getCompanyName());
	    assertEquals("XYZ Solutions", response.get(1).getCompanyName());

	    verify(customerRepository, times(1))
	            .findAll();
	}
	@Test
	void getAllCustomers_ShouldReturnEmptyList_WhenNoCustomersExist() {

	    // Arrange
	    when(customerRepository.findAll())
	            .thenReturn(List.of());

	    // Act
	    List<CustomerResponse> response = customerService.getAllCustomers();

	    // Assert
	    assertNotNull(response);
	    assertTrue(response.isEmpty());

	    verify(customerRepository, times(1))
	            .findAll();
	}
	@Test
	void updateCustomer_ShouldUpdateCustomerSuccessfully() {

	    // Arrange
	    Long customerId = 1L;

	    CustomerRequest request = new CustomerRequest();
	    request.setCompanyName("ABC Technologies Updated");
	    request.setContactPerson("Rahul Sharma");
	    request.setEmail("rahul@abc.com");
	    request.setPhone("9999999999");
	    request.setAddress("Hyderabad");

	    Customer existingCustomer = Customer.builder()
	            .id(customerId)
	            .companyName("ABC Technologies")
	            .contactPerson("Rahul")
	            .email("rahul@abc.com")
	            .phone("9876543210")
	            .address("Hyderabad")
	            .build();

	    when(customerRepository.findById(customerId))
	            .thenReturn(Optional.of(existingCustomer));

	    // VERY IMPORTANT - mock duplicate company check
	    when(customerRepository.findByCompanyName("ABC Technologies Updated"))
	            .thenReturn(Optional.empty());

	    when(customerRepository.save(any(Customer.class)))
	            .thenAnswer(invocation -> invocation.getArgument(0));

	    // Act
	    CustomerResponse response = customerService.updateCustomer(customerId, request);

	    // Assert
	    assertNotNull(response);
	    assertEquals("ABC Technologies Updated", response.getCompanyName());
	    assertEquals("Rahul Sharma", response.getContactPerson());
	    assertEquals("rahul@abc.com", response.getEmail());
	    assertEquals("9999999999", response.getPhone());
	    assertEquals("Hyderabad", response.getAddress());

	    verify(customerRepository).findById(customerId);
	    verify(customerRepository).findByCompanyName("ABC Technologies Updated");
	    verify(customerRepository).save(any(Customer.class));
	}
	@Test
	void updateCustomer_ShouldThrowException_WhenCustomerNotFound() {

	    // Arrange
	    Long customerId = 1L;

	    CustomerRequest request = new CustomerRequest();
	    request.setCompanyName("ABC Technologies");

	    when(customerRepository.findById(customerId))
	            .thenReturn(Optional.empty());

	    // Act & Assert
	    ResourceNotFoundException exception = assertThrows(
	            ResourceNotFoundException.class,
	            () -> customerService.updateCustomer(customerId, request)
	    );

	    assertEquals(
	            "Customer not found with id: 1",
	            exception.getMessage()
	    );

	    verify(customerRepository).findById(customerId);
	    verify(customerRepository, never()).save(any(Customer.class));
	}
	@Test
	void updateCustomer_ShouldThrowException_WhenCompanyNameAlreadyExists() {

	    // Arrange
	    Long customerId = 1L;

	    CustomerRequest request = new CustomerRequest();
	    request.setCompanyName("ABC Technologies");

	    Customer existingCustomer = Customer.builder()
	            .id(customerId)
	            .companyName("Old Company")
	            .build();

	    Customer duplicateCustomer = Customer.builder()
	            .id(2L)
	            .companyName("ABC Technologies")
	            .build();

	    when(customerRepository.findById(customerId))
	            .thenReturn(Optional.of(existingCustomer));

	    when(customerRepository.findByCompanyName("ABC Technologies"))
	            .thenReturn(Optional.of(duplicateCustomer));

	    // Act & Assert
	    IllegalArgumentException exception = assertThrows(
	            IllegalArgumentException.class,
	            () -> customerService.updateCustomer(customerId, request)
	    );

	    assertEquals(
	            "Another customer already exists with company name: ABC Technologies",
	            exception.getMessage()
	    );

	    verify(customerRepository).findById(customerId);
	    verify(customerRepository).findByCompanyName("ABC Technologies");
	    verify(customerRepository, never()).save(any(Customer.class));
	}
	@Test
	void deleteCustomer_ShouldDeleteCustomerSuccessfully() {

	    // Arrange
	    Long customerId = 1L;

	    Customer customer = Customer.builder()
	            .id(customerId)
	            .companyName("ABC Technologies")
	            .contactPerson("Rahul Sharma")
	            .email("rahul@abc.com")
	            .phone("9876543210")
	            .address("Hyderabad")
	            .build();

	    when(customerRepository.findById(customerId))
	            .thenReturn(Optional.of(customer));

	    doNothing().when(customerRepository).delete(customer);

	    // Act
	    customerService.deleteCustomer(customerId);

	    // Assert
	    verify(customerRepository, times(1))
	            .findById(customerId);

	    verify(customerRepository, times(1))
	            .delete(customer);
	}
	@Test
	void deleteCustomer_ShouldThrowException_WhenCustomerNotFound() {

	    // Arrange
	    Long customerId = 1L;

	    when(customerRepository.findById(customerId))
	            .thenReturn(Optional.empty());

	    // Act & Assert
	    ResourceNotFoundException exception = assertThrows(
	            ResourceNotFoundException.class,
	            () -> customerService.deleteCustomer(customerId)
	    );

	    assertEquals(
	            "Customer not found with id: 1",
	            exception.getMessage()
	    );

	    verify(customerRepository, times(1))
	            .findById(customerId);

	    verify(customerRepository, never())
	            .delete(any(Customer.class));
	}
}
