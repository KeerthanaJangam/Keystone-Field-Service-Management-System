package com.keystone.deliverableservice.service;

import com.keystone.deliverableservice.dto.request.SiteRequest;
import com.keystone.deliverableservice.dto.response.SiteResponse;
import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.entity.Site;
import com.keystone.deliverableservice.exception.ResourceNotFoundException;
import com.keystone.deliverableservice.repository.CustomerRepository;
import com.keystone.deliverableservice.repository.SiteRepository;
import com.keystone.deliverableservice.service.impl.SiteServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SiteServiceImplTest
{
	 @Mock
	 private SiteRepository siteRepository;

	 @Mock
	 private CustomerRepository customerRepository;

	 @InjectMocks
	 private SiteServiceImpl siteService;
	 
	 @Test
	 void createSite_ShouldCreateSiteSuccessfully() {

	     // Arrange
	     Customer customer = Customer.builder()
	             .id(1L)
	             .companyName("ABC Technologies")
	             .build();

	     SiteRequest request = new SiteRequest();
	     request.setSiteName("Hyderabad Office");
	     request.setAddress("Madhapur");
	     request.setCity("Hyderabad");
	     request.setState("Telangana");
	     request.setPincode("500081");
	     request.setCustomerId(1L);

	     when(customerRepository.findById(1L))
	             .thenReturn(Optional.of(customer));

	     Site savedSite = Site.builder()
	             .id(1L)
	             .siteName("Hyderabad Office")
	             .address("Madhapur")
	             .city("Hyderabad")
	             .state("Telangana")
	             .pincode("500081")
	             .customer(customer)
	             .build();

	     when(siteRepository.save(any(Site.class)))
	             .thenReturn(savedSite);

	     // Act
	     SiteResponse response = siteService.createSite(request);

	     // Assert
	     assertNotNull(response);
	     assertEquals(1L, response.getId());
	     assertEquals("Hyderabad Office", response.getSiteName());
	     assertEquals("Hyderabad", response.getCity());

	     verify(customerRepository).findById(1L);
	     verify(siteRepository).save(any(Site.class));
	 }
	 @Test
	 void createSite_ShouldThrowException_WhenCustomerNotFound() {

	     // Arrange
	     SiteRequest request = new SiteRequest();
	     request.setCustomerId(1L);

	     when(customerRepository.findById(1L))
	             .thenReturn(Optional.empty());

	     // Act & Assert
	     ResourceNotFoundException exception =
	             assertThrows(
	                     ResourceNotFoundException.class,
	                     () -> siteService.createSite(request)
	             );

	     assertEquals(
	             "Customer not found with id: 1",
	             exception.getMessage()
	     );

	     verify(customerRepository).findById(1L);

	     verify(siteRepository, never())
	             .save(any(Site.class));
	 }
	 @Test
	 void getSiteById_ShouldReturnSite_WhenSiteExists() {

	     // Arrange
	     Customer customer = Customer.builder()
	             .id(1L)
	             .companyName("ABC Technologies")
	             .build();

	     Site site = Site.builder()
	             .id(1L)
	             .siteName("Hyderabad Office")
	             .address("Madhapur")
	             .city("Hyderabad")
	             .state("Telangana")
	             .pincode("500081")
	             .customer(customer)
	             .build();

	     when(siteRepository.findById(1L))
	             .thenReturn(Optional.of(site));

	     // Act
	     SiteResponse response = siteService.getSiteById(1L);

	     // Assert
	     assertNotNull(response);
	     assertEquals(1L, response.getId());
	     assertEquals("Hyderabad Office", response.getSiteName());
	     assertEquals("Hyderabad", response.getCity());
	     assertEquals("ABC Technologies", response.getCustomerName());

	     verify(siteRepository).findById(1L);
	 }
	 @Test
	 void getSiteById_ShouldThrowException_WhenSiteNotFound() {

	     // Arrange
	     when(siteRepository.findById(1L))
	             .thenReturn(Optional.empty());

	     // Act & Assert
	     ResourceNotFoundException exception =
	             assertThrows(
	                     ResourceNotFoundException.class,
	                     () -> siteService.getSiteById(1L)
	             );

	     assertEquals(
	             "Site not found with id: 1",
	             exception.getMessage()
	     );

	     verify(siteRepository).findById(1L);
	 }
	 @Test
	 void getAllSites_ShouldReturnSiteList() {

	     // Arrange
	     Customer customer = Customer.builder()
	             .id(1L)
	             .companyName("ABC Technologies")
	             .build();

	     Site site1 = Site.builder()
	             .id(1L)
	             .siteName("Hyderabad Office")
	             .address("Madhapur")
	             .city("Hyderabad")
	             .state("Telangana")
	             .pincode("500081")
	             .customer(customer)
	             .build();

	     Site site2 = Site.builder()
	             .id(2L)
	             .siteName("Bangalore Office")
	             .address("Whitefield")
	             .city("Bangalore")
	             .state("Karnataka")
	             .pincode("560066")
	             .customer(customer)
	             .build();

	     when(siteRepository.findAll())
	             .thenReturn(List.of(site1, site2));

	     // Act
	     List<SiteResponse> response = siteService.getAllSites();

	     // Assert
	     assertNotNull(response);
	     assertEquals(2, response.size());

	     assertEquals("Hyderabad Office", response.get(0).getSiteName());
	     assertEquals("Bangalore Office", response.get(1).getSiteName());

	     verify(siteRepository).findAll();
	 }
	 @Test
	 void getAllSites_ShouldReturnEmptyList_WhenNoSitesExist() {

	     // Arrange
	     when(siteRepository.findAll())
	             .thenReturn(List.of());

	     // Act
	     List<SiteResponse> response = siteService.getAllSites();

	     // Assert
	     assertNotNull(response);
	     assertTrue(response.isEmpty());

	     verify(siteRepository).findAll();
	 }
	 @Test
	 void getSitesByCustomer_ShouldReturnSites_WhenCustomerExists() {

	     // Arrange
	     Long customerId = 1L;

	     Customer customer = Customer.builder()
	             .id(customerId)
	             .companyName("ABC Technologies")
	             .build();

	     Site site1 = Site.builder()
	             .id(1L)
	             .siteName("Hyderabad Office")
	             .address("Madhapur")
	             .city("Hyderabad")
	             .state("Telangana")
	             .pincode("500081")
	             .customer(customer)
	             .build();

	     Site site2 = Site.builder()
	             .id(2L)
	             .siteName("Bangalore Office")
	             .address("Whitefield")
	             .city("Bangalore")
	             .state("Karnataka")
	             .pincode("560066")
	             .customer(customer)
	             .build();

	     when(customerRepository.findById(customerId))
	             .thenReturn(Optional.of(customer));

	     when(siteRepository.findByCustomer(customer))
	             .thenReturn(List.of(site1, site2));

	     // Act
	     List<SiteResponse> response = siteService.getSitesByCustomer(customerId);

	     // Assert
	     assertNotNull(response);
	     assertEquals(2, response.size());

	     assertEquals("Hyderabad Office", response.get(0).getSiteName());
	     assertEquals("Bangalore Office", response.get(1).getSiteName());

	     verify(customerRepository).findById(customerId);
	     verify(siteRepository).findByCustomer(customer);
	 }
	 @Test
	 void getSitesByCustomer_ShouldReturnEmptyList_WhenCustomerHasNoSites() {

	     // Arrange
	     Long customerId = 1L;

	     Customer customer = Customer.builder()
	             .id(customerId)
	             .companyName("ABC Technologies")
	             .build();

	     when(customerRepository.findById(customerId))
	             .thenReturn(Optional.of(customer));

	     when(siteRepository.findByCustomer(customer))
	             .thenReturn(List.of());

	     // Act
	     List<SiteResponse> response = siteService.getSitesByCustomer(customerId);

	     // Assert
	     assertNotNull(response);
	     assertTrue(response.isEmpty());

	     verify(customerRepository).findById(customerId);
	     verify(siteRepository).findByCustomer(customer);
	 }
	 @Test
	 void getSitesByCustomer_ShouldThrowException_WhenCustomerNotFound() {

	     // Arrange
	     Long customerId = 1L;

	     when(customerRepository.findById(customerId))
	             .thenReturn(Optional.empty());

	     // Act & Assert
	     ResourceNotFoundException exception = assertThrows(
	             ResourceNotFoundException.class,
	             () -> siteService.getSitesByCustomer(customerId)
	     );

	     assertEquals(
	             "Customer not found with id: 1",
	             exception.getMessage()
	     );

	     verify(customerRepository).findById(customerId);
	     verify(siteRepository, never()).findByCustomer(any(Customer.class));
	 }
	 @Test
	 void updateSite_ShouldUpdateSiteSuccessfully() {

	     // Arrange
	     Long siteId = 1L;

	     Customer customer = Customer.builder()
	             .id(1L)
	             .companyName("ABC Technologies")
	             .build();

	     SiteRequest request = new SiteRequest();
	     request.setSiteName("Updated Hyderabad Office");
	     request.setAddress("Hi-Tech City");
	     request.setCity("Hyderabad");
	     request.setState("Telangana");
	     request.setPincode("500081");
	     request.setCustomerId(1L);

	     Site existingSite = Site.builder()
	             .id(siteId)
	             .siteName("Old Site")
	             .address("Old Address")
	             .customer(customer)
	             .build();

	     when(siteRepository.findById(siteId))
	             .thenReturn(Optional.of(existingSite));

	     when(customerRepository.findById(1L))
	             .thenReturn(Optional.of(customer));

	     when(siteRepository.save(any(Site.class)))
	             .thenAnswer(invocation -> invocation.getArgument(0));

	     // Act
	     SiteResponse response = siteService.updateSite(siteId, request);

	     // Assert
	     assertNotNull(response);
	     assertEquals("Updated Hyderabad Office", response.getSiteName());
	     assertEquals("Hyderabad", response.getCity());

	     verify(siteRepository).findById(siteId);
	     verify(customerRepository).findById(1L);
	     verify(siteRepository).save(any(Site.class));
	 }
	 @Test
	 void updateSite_ShouldThrowException_WhenSiteNotFound() {

	     // Arrange
	     Long siteId = 1L;

	     SiteRequest request = new SiteRequest();
	     request.setCustomerId(1L);

	     when(siteRepository.findById(siteId))
	             .thenReturn(Optional.empty());

	     // Act & Assert
	     ResourceNotFoundException exception = assertThrows(
	             ResourceNotFoundException.class,
	             () -> siteService.updateSite(siteId, request)
	     );

	     assertEquals(
	             "Site not found with id: 1",
	             exception.getMessage()
	     );

	     verify(siteRepository).findById(siteId);

	     verify(customerRepository, never())
	             .findById(anyLong());

	     verify(siteRepository, never())
	             .save(any(Site.class));
	 }
	 @Test
	 void updateSite_ShouldThrowException_WhenCustomerNotFound() {

	     // Arrange
	     Long siteId = 1L;

	     SiteRequest request = new SiteRequest();
	     request.setCustomerId(1L);

	     Site site = Site.builder()
	             .id(siteId)
	             .build();

	     when(siteRepository.findById(siteId))
	             .thenReturn(Optional.of(site));

	     when(customerRepository.findById(1L))
	             .thenReturn(Optional.empty());

	     // Act & Assert
	     ResourceNotFoundException exception = assertThrows(
	             ResourceNotFoundException.class,
	             () -> siteService.updateSite(siteId, request)
	     );

	     assertEquals(
	             "Customer not found with id: 1",
	             exception.getMessage()
	     );

	     verify(siteRepository).findById(siteId);
	     verify(customerRepository).findById(1L);

	     verify(siteRepository, never())
	             .save(any(Site.class));
	 }
	 @Test
	 void deleteSite_ShouldDeleteSiteSuccessfully() {

	     // Arrange
	     Long siteId = 1L;

	     Site site = Site.builder()
	             .id(siteId)
	             .siteName("Hyderabad Office")
	             .build();

	     when(siteRepository.findById(siteId))
	             .thenReturn(Optional.of(site));

	     doNothing().when(siteRepository).delete(site);

	     // Act
	     siteService.deleteSite(siteId);

	     // Assert
	     verify(siteRepository).findById(siteId);
	     verify(siteRepository).delete(site);
	 }
	 @Test
	 void deleteSite_ShouldThrowException_WhenSiteNotFound() {

	     // Arrange
	     Long siteId = 1L;

	     when(siteRepository.findById(siteId))
	             .thenReturn(Optional.empty());

	     // Act & Assert
	     ResourceNotFoundException exception = assertThrows(
	             ResourceNotFoundException.class,
	             () -> siteService.deleteSite(siteId)
	     );

	     assertEquals(
	             "Site not found with id: 1",
	             exception.getMessage()
	     );

	     verify(siteRepository).findById(siteId);

	     verify(siteRepository, never())
	             .delete(any(Site.class));
	 }
}
