package com.keystone.deliverableservice.service;

import com.keystone.deliverableservice.dto.request.AssignTechnicianRequest;
import com.keystone.deliverableservice.dto.request.WorkOrderRequest;
import com.keystone.deliverableservice.dto.response.WorkOrderResponse;
import com.keystone.deliverableservice.entity.*;
import com.keystone.deliverableservice.enums.Priority;
import com.keystone.deliverableservice.enums.Role;
import com.keystone.deliverableservice.enums.WorkOrderStatus;
import com.keystone.deliverableservice.exception.ResourceNotFoundException;
import com.keystone.deliverableservice.repository.CustomerRepository;
import com.keystone.deliverableservice.repository.SiteRepository;
import com.keystone.deliverableservice.repository.UserRepository;
import com.keystone.deliverableservice.repository.WorkOrderRepository;
import com.keystone.deliverableservice.repository.WorkOrderStatusHistoryRepository;
import com.keystone.deliverableservice.service.impl.WorkOrderServiceImpl;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class WorkOrderServiceImplTest
{
	   @Mock
	    private WorkOrderRepository workOrderRepository;

	    @Mock
	    private CustomerRepository customerRepository;

	    @Mock
	    private SiteRepository siteRepository;

	    @Mock
	    private UserRepository userRepository;

	    @Mock
	    private WorkOrderStatusHistoryRepository historyRepository;

	    @InjectMocks
	    private WorkOrderServiceImpl workOrderService;
	    
	    @Test
	    void createWorkOrder_ShouldCreateWorkOrderSuccessfully() {

	        // Arrange
	        Customer customer = Customer.builder()
	                .id(1L)
	                .companyName("ABC Technologies")
	                .build();

	        Site site = Site.builder()
	                .id(1L)
	                .siteName("Hyderabad Office")
	                .customer(customer)
	                .build();

	        WorkOrderRequest request = new WorkOrderRequest();
	        request.setTitle("Printer Repair");
	        request.setDescription("Printer is not working");
	        request.setPriority(Priority.HIGH);
	        request.setCustomerId(1L);
	        request.setSiteId(1L);

	        when(customerRepository.findById(1L))
	                .thenReturn(Optional.of(customer));

	        when(siteRepository.findById(1L))
	                .thenReturn(Optional.of(site));

	        when(workOrderRepository.count())
	                .thenReturn(0L);

	        WorkOrder savedWorkOrder = WorkOrder.builder()
	                .id(1L)
	                .workOrderCode("WO-00001")
	                .title("Printer Repair")
	                .description("Printer is not working")
	                .priority(Priority.HIGH)
	                .status(WorkOrderStatus.NEW)
	                .slaDueDate(LocalDate.now().plusDays(7))
	                .customer(customer)
	                .site(site)
	                .build();

	        when(workOrderRepository.save(any(WorkOrder.class)))
	                .thenReturn(savedWorkOrder);

	        // Act
	        WorkOrderResponse response =
	                workOrderService.createWorkOrder(request);

	        // Assert
	        assertNotNull(response);

	        assertEquals("WO-00001", response.getWorkOrderCode());

	        assertEquals("Printer Repair", response.getTitle());

	        assertEquals(WorkOrderStatus.NEW, response.getStatus());

	        verify(customerRepository).findById(1L);

	        verify(siteRepository).findById(1L);

	        verify(workOrderRepository).count();

	        verify(workOrderRepository).save(any(WorkOrder.class));

	        verify(historyRepository).save(any(WorkOrderStatusHistory.class));
	    }
	    @Test
	    void createWorkOrder_ShouldThrowException_WhenCustomerNotFound() {

	        // Arrange
	        WorkOrderRequest request = new WorkOrderRequest();
	        request.setCustomerId(1L);
	        request.setSiteId(1L);

	        when(customerRepository.findById(1L))
	                .thenReturn(Optional.empty());

	        // Act & Assert
	        ResourceNotFoundException exception = assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.createWorkOrder(request)
	        );

	        assertEquals("Customer not found", exception.getMessage());

	        verify(customerRepository).findById(1L);

	        verify(siteRepository, never())
	                .findById(anyLong());

	        verify(workOrderRepository, never())
	                .save(any(WorkOrder.class));

	        verify(historyRepository, never())
	                .save(any(WorkOrderStatusHistory.class));
	    }
	    @Test
	    void createWorkOrder_ShouldThrowException_WhenSiteNotFound() {

	        // Arrange
	        Customer customer = Customer.builder()
	                .id(1L)
	                .companyName("ABC Technologies")
	                .build();

	        WorkOrderRequest request = new WorkOrderRequest();
	        request.setCustomerId(1L);
	        request.setSiteId(1L);

	        when(customerRepository.findById(1L))
	                .thenReturn(Optional.of(customer));

	        when(siteRepository.findById(1L))
	                .thenReturn(Optional.empty());

	        // Act & Assert
	        ResourceNotFoundException exception = assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.createWorkOrder(request)
	        );

	        assertEquals("Site not found", exception.getMessage());

	        verify(customerRepository).findById(1L);

	        verify(siteRepository).findById(1L);

	        verify(workOrderRepository, never())
	                .save(any(WorkOrder.class));

	        verify(historyRepository, never())
	                .save(any(WorkOrderStatusHistory.class));
	    }
	    @Test
	    void assignTechnician_ShouldAssignSuccessfully() {

	        // Arrange
	        Long workOrderId = 1L;

	        Customer customer = Customer.builder()
	                .id(1L)
	                .companyName("ABC Technologies")
	                .build();

	        Site site = Site.builder()
	                .id(1L)
	                .siteName("Hyderabad Office")
	                .customer(customer)
	                .build();

	        User technician = User.builder()
	                .id(2L)
	                .name("John")
	                .role(Role.TECHNICIAN)
	                .build();

	        WorkOrder workOrder = WorkOrder.builder()
	                .id(workOrderId)
	                .workOrderCode("WO-00001")
	                .title("Printer Repair")
	                .status(WorkOrderStatus.NEW)
	                .customer(customer)
	                .site(site)
	                .build();

	        AssignTechnicianRequest request = new AssignTechnicianRequest();
	        request.setTechnicianId(2L);

	        when(workOrderRepository.findById(workOrderId))
	                .thenReturn(Optional.of(workOrder));

	        when(userRepository.findById(2L))
	                .thenReturn(Optional.of(technician));

	        when(workOrderRepository.save(any(WorkOrder.class)))
	                .thenAnswer(invocation -> invocation.getArgument(0));

	        // Act
	        WorkOrderResponse response =
	                workOrderService.assignTechnician(workOrderId, request);

	        // Assert
	        assertNotNull(response);

	        assertEquals(
	                WorkOrderStatus.ASSIGNED,
	                response.getStatus()
	        );

	        assertEquals(
	                "John",
	                response.getTechnicianName()
	        );

	        verify(workOrderRepository).findById(workOrderId);
	        verify(userRepository).findById(2L);
	        verify(workOrderRepository).save(any(WorkOrder.class));
	        verify(historyRepository).save(any(WorkOrderStatusHistory.class));
	    }
	    @Test
	    void assignTechnician_ShouldThrowException_WhenWorkOrderNotFound() {

	        // Arrange
	        Long workOrderId = 1L;

	        AssignTechnicianRequest request = new AssignTechnicianRequest();
	        request.setTechnicianId(2L);

	        when(workOrderRepository.findById(workOrderId))
	                .thenReturn(Optional.empty());

	        // Act & Assert
	        ResourceNotFoundException exception = assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.assignTechnician(workOrderId, request)
	        );

	        assertEquals(
	                "Work Order not found",
	                exception.getMessage()
	        );

	        verify(workOrderRepository).findById(workOrderId);

	        verify(userRepository, never())
	                .findById(anyLong());

	        verify(workOrderRepository, never())
	                .save(any());

	        verify(historyRepository, never())
	                .save(any());
	    }
	    @Test
	    void assignTechnician_ShouldThrowException_WhenTechnicianNotFound() {

	        // Arrange
	        Long workOrderId = 1L;

	        WorkOrder workOrder = WorkOrder.builder()
	                .id(workOrderId)
	                .status(WorkOrderStatus.NEW)
	                .build();

	        AssignTechnicianRequest request = new AssignTechnicianRequest();
	        request.setTechnicianId(2L);

	        when(workOrderRepository.findById(workOrderId))
	                .thenReturn(Optional.of(workOrder));

	        when(userRepository.findById(2L))
	                .thenReturn(Optional.empty());

	        // Act & Assert
	        ResourceNotFoundException exception = assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.assignTechnician(workOrderId, request)
	        );

	        assertEquals(
	                "Technician not found",
	                exception.getMessage()
	        );

	        verify(workOrderRepository).findById(workOrderId);
	        verify(userRepository).findById(2L);

	        verify(workOrderRepository, never())
	                .save(any());

	        verify(historyRepository, never())
	                .save(any());
	    }
	    @Test
	    void assignTechnician_ShouldThrowException_WhenUserIsNotTechnician() {

	        // Arrange
	        Long workOrderId = 1L;

	        WorkOrder workOrder = WorkOrder.builder()
	                .id(workOrderId)
	                .status(WorkOrderStatus.NEW)
	                .build();

	        User dispatcher = User.builder()
	                .id(2L)
	                .role(Role.DISPATCHER)
	                .build();

	        AssignTechnicianRequest request = new AssignTechnicianRequest();
	        request.setTechnicianId(2L);

	        when(workOrderRepository.findById(workOrderId))
	                .thenReturn(Optional.of(workOrder));

	        when(userRepository.findById(2L))
	                .thenReturn(Optional.of(dispatcher));

	        // Act & Assert
	        IllegalArgumentException exception = assertThrows(
	                IllegalArgumentException.class,
	                () -> workOrderService.assignTechnician(workOrderId, request)
	        );

	        assertEquals(
	                "Selected user is not a technician.",
	                exception.getMessage()
	        );

	        verify(workOrderRepository).findById(workOrderId);
	        verify(userRepository).findById(2L);

	        verify(workOrderRepository, never())
	                .save(any());

	        verify(historyRepository, never())
	                .save(any());
	    }
	    @Test
	    void getWorkOrder_ShouldReturnWorkOrder_WhenWorkOrderExists() {

	        // Arrange
	        Customer customer = Customer.builder()
	                .id(1L)
	                .companyName("ABC Technologies")
	                .build();

	        Site site = Site.builder()
	                .id(1L)
	                .siteName("Hyderabad Office")
	                .customer(customer)
	                .build();

	        WorkOrder workOrder = WorkOrder.builder()
	                .id(1L)
	                .workOrderCode("WO-00001")
	                .title("Printer Repair")
	                .description("Printer issue")
	                .priority(Priority.HIGH)
	                .status(WorkOrderStatus.NEW)
	                .customer(customer)
	                .site(site)
	                .build();

	        when(workOrderRepository.findById(1L))
	                .thenReturn(Optional.of(workOrder));

	        // Act
	        WorkOrderResponse response = workOrderService.getWorkOrder(1L);

	        // Assert
	        assertNotNull(response);
	        assertEquals(1L, response.getId());
	        assertEquals("WO-00001", response.getWorkOrderCode());
	        assertEquals("Printer Repair", response.getTitle());

	        verify(workOrderRepository).findById(1L);
	    }
	    @Test
	    void getWorkOrder_ShouldThrowException_WhenWorkOrderNotFound() {

	        when(workOrderRepository.findById(1L))
	                .thenReturn(Optional.empty());

	        ResourceNotFoundException exception = assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.getWorkOrder(1L)
	        );

	        assertEquals("Work Order not found", exception.getMessage());

	        verify(workOrderRepository).findById(1L);
	    }
	    @Test
	    void getAllWorkOrders_ShouldReturnWorkOrderList() {

	        Customer customer = Customer.builder()
	                .id(1L)
	                .companyName("ABC Technologies")
	                .build();

	        Site site = Site.builder()
	                .id(1L)
	                .siteName("Hyderabad Office")
	                .customer(customer)
	                .build();

	        WorkOrder workOrder1 = WorkOrder.builder()
	                .id(1L)
	                .workOrderCode("WO-00001")
	                .title("Printer Repair")
	                .status(WorkOrderStatus.NEW)
	                .customer(customer)
	                .site(site)
	                .build();

	        WorkOrder workOrder2 = WorkOrder.builder()
	                .id(2L)
	                .workOrderCode("WO-00002")
	                .title("Network Issue")
	                .status(WorkOrderStatus.ASSIGNED)
	                .customer(customer)
	                .site(site)
	                .build();

	        when(workOrderRepository.findAll())
	                .thenReturn(List.of(workOrder1, workOrder2));

	        List<WorkOrderResponse> response =
	                workOrderService.getAllWorkOrders();

	        assertEquals(2, response.size());

	        verify(workOrderRepository).findAll();
	    }
	    @Test
	    void getAllWorkOrders_ShouldReturnEmptyList() {

	        when(workOrderRepository.findAll())
	                .thenReturn(List.of());

	        List<WorkOrderResponse> response =
	                workOrderService.getAllWorkOrders();

	        assertTrue(response.isEmpty());

	        verify(workOrderRepository).findAll();
	    }
	    @Test
	    void updateWorkOrder_ShouldUpdateSuccessfully() {

	        Customer customer = Customer.builder()
	                .id(1L)
	                .companyName("ABC")
	                .build();

	        Site site = Site.builder()
	                .id(1L)
	                .siteName("Office")
	                .customer(customer)
	                .build();

	        WorkOrder workOrder = WorkOrder.builder()
	                .id(1L)
	                .title("Old Title")
	                .customer(customer)
	                .site(site)
	                .build();

	        WorkOrderRequest request = new WorkOrderRequest();
	        request.setTitle("Updated Title");
	        request.setDescription("Updated");
	        request.setPriority(Priority.HIGH);
	        request.setCustomerId(1L);
	        request.setSiteId(1L);

	        when(workOrderRepository.findById(1L))
	                .thenReturn(Optional.of(workOrder));

	        when(customerRepository.findById(1L))
	                .thenReturn(Optional.of(customer));

	        when(siteRepository.findById(1L))
	                .thenReturn(Optional.of(site));

	        when(workOrderRepository.save(any(WorkOrder.class)))
	                .thenAnswer(invocation -> invocation.getArgument(0));

	        WorkOrderResponse response =
	                workOrderService.updateWorkOrder(1L, request);

	        assertEquals("Updated Title", response.getTitle());

	        verify(workOrderRepository).save(any(WorkOrder.class));
	    }
	    @Test
	    void updateWorkOrder_ShouldThrowException_WhenWorkOrderNotFound() {

	        WorkOrderRequest request = new WorkOrderRequest();

	        when(workOrderRepository.findById(1L))
	                .thenReturn(Optional.empty());

	        assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.updateWorkOrder(1L, request)
	        );

	        verify(workOrderRepository).findById(1L);
	    }
	    @Test
	    void updateWorkOrder_ShouldThrowException_WhenCustomerNotFound() {

	        WorkOrder workOrder = WorkOrder.builder()
	                .id(1L)
	                .build();

	        WorkOrderRequest request = new WorkOrderRequest();
	        request.setCustomerId(1L);

	        when(workOrderRepository.findById(1L))
	                .thenReturn(Optional.of(workOrder));

	        when(customerRepository.findById(1L))
	                .thenReturn(Optional.empty());

	        assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.updateWorkOrder(1L, request)
	        );
	    }
	    @Test
	    void deleteWorkOrder_ShouldDeleteSuccessfully() {

	        WorkOrder workOrder = WorkOrder.builder()
	                .id(1L)
	                .build();

	        when(workOrderRepository.findById(1L))
	                .thenReturn(Optional.of(workOrder));

	        doNothing().when(workOrderRepository).delete(workOrder);

	        workOrderService.deleteWorkOrder(1L);

	        verify(workOrderRepository).delete(workOrder);
	    }
	    @Test
	    void deleteWorkOrder_ShouldThrowException_WhenNotFound() {

	        when(workOrderRepository.findById(1L))
	                .thenReturn(Optional.empty());

	        assertThrows(
	                ResourceNotFoundException.class,
	                () -> workOrderService.deleteWorkOrder(1L)
	        );

	        verify(workOrderRepository, never())
	                .delete(any());
	    }
}
