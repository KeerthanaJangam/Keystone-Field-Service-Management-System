package com.keystone.deliverableservice.service.impl;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.deliverableservice.dto.request.AssignTechnicianRequest;
import com.keystone.deliverableservice.dto.request.WorkOrderRequest;
import com.keystone.deliverableservice.dto.response.WorkOrderResponse;
import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.entity.Site;
import com.keystone.deliverableservice.entity.User;
import com.keystone.deliverableservice.entity.WorkOrder;
import com.keystone.deliverableservice.entity.WorkOrderStatusHistory;
import com.keystone.deliverableservice.enums.Role;
import com.keystone.deliverableservice.enums.WorkOrderStatus;
import com.keystone.deliverableservice.exception.ResourceNotFoundException;
import com.keystone.deliverableservice.mapper.WorkOrderMapper;
import com.keystone.deliverableservice.repository.CustomerRepository;
import com.keystone.deliverableservice.repository.SiteRepository;
import com.keystone.deliverableservice.repository.UserRepository;
import com.keystone.deliverableservice.repository.WorkOrderRepository;
import com.keystone.deliverableservice.repository.WorkOrderStatusHistoryRepository;
import com.keystone.deliverableservice.service.WorkOrderService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Transactional
public class WorkOrderServiceImpl implements WorkOrderService 
{ 
	   private final WorkOrderRepository workOrderRepository;
	    private final CustomerRepository customerRepository;
	    private final SiteRepository siteRepository;
	    private final UserRepository userRepository;
	    private final WorkOrderStatusHistoryRepository historyRepository;

	    private static final Logger logger =
	            LoggerFactory.getLogger(WorkOrderServiceImpl.class);
	    
	    @Override
	    public WorkOrderResponse createWorkOrder(WorkOrderRequest request) {
	    	logger.info("Creating work order with title: {}", request.getTitle());
	    	
	        Customer customer = customerRepository.findById(request.getCustomerId())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Customer not found"));

	        Site site = siteRepository.findById(request.getSiteId())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Site not found"));

	        WorkOrder workOrder = WorkOrderMapper.toEntity(request, customer, site);

	        workOrder.setWorkOrderCode(generateWorkOrderCode());

	        logger.debug("Generated work order code: {}", workOrder.getWorkOrderCode());
	        
	        workOrder.setSlaDueDate(LocalDate.now().plusDays(7));

	        WorkOrder saved = workOrderRepository.save(workOrder);

	        saveStatusHistory(saved, null, saved.getStatus(), "Work Order Created", null);

	        logger.info("Work order {} created successfully.", workOrder.getWorkOrderCode());
	        return WorkOrderMapper.toResponse(saved);
	    }

	    @Override
	    public WorkOrderResponse updateWorkOrder(Long id,
	                                             WorkOrderRequest request) {
	    	logger.info("Updating work order ID: {}", id);
	    	
	        WorkOrder workOrder = workOrderRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Work Order not found"));

	        Customer customer = customerRepository.findById(request.getCustomerId())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Customer not found"));

	        Site site = siteRepository.findById(request.getSiteId())
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Site not found"));

	        WorkOrderMapper.updateEntity(workOrder, request, customer, site);

	        WorkOrder updated = workOrderRepository.save(workOrder);

	        return WorkOrderMapper.toResponse(updated);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public WorkOrderResponse getWorkOrder(Long id) {

	    	logger.debug("Fetching work order ID: {}", id);
	    	
	        WorkOrder workOrder = workOrderRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Work Order not found"));

	        return WorkOrderMapper.toResponse(workOrder);
	    }

	    @Override
	    @Transactional(readOnly = true)
	    public List<WorkOrderResponse> getAllWorkOrders() {

	        return workOrderRepository.findAll()
	                .stream()
	                .map(WorkOrderMapper::toResponse)
	                .collect(Collectors.toList());
	    }

	    @Override
	    public WorkOrderResponse assignTechnician(Long workOrderId,
	                                              AssignTechnicianRequest request) {
	    	
	    	logger.info(
	                "Assigning technician {} to work order {}",
	                request.getTechnicianId(),
	                workOrderId
	        );
	    	
	    	WorkOrder workOrder = workOrderRepository.findById(workOrderId)
	                .orElseThrow(() -> {
	                    logger.error("Work Order not found with ID: {}", workOrderId);
	                    return new ResourceNotFoundException("Work Order not found");
	                });
	    	
	    	 User technician = userRepository.findById(request.getTechnicianId())
	    	            .orElseThrow(() -> {
	    	                logger.error("Technician not found with ID: {}", request.getTechnicianId());
	    	                return new ResourceNotFoundException("Technician not found");
	    	            });
	    	 
	        if (technician.getRole() != Role.TECHNICIAN) {
	        	logger.warn(
	                    "User with ID {} is not a technician.",
	                    request.getTechnicianId()
	            );
	            throw new IllegalArgumentException("Selected user is not a technician.");
	        }

	        WorkOrderStatus previousStatus = workOrder.getStatus();

	        workOrder.setAssignedTechnician(technician);
	        workOrder.setStatus(WorkOrderStatus.ASSIGNED);

	        WorkOrder saved = workOrderRepository.save(workOrder);

	        saveStatusHistory(
	                saved,
	                previousStatus,
	                WorkOrderStatus.ASSIGNED,
	                "Assigned to technician",
	                technician
	        );
	        logger.info(
	                "Technician assigned successfully to work order {}",
	                workOrderId
	        );
	        return WorkOrderMapper.toResponse(saved);
	    }

	    @Override
	    public void deleteWorkOrder(Long id) {

	    	logger.info("Deleting work order ID: {}", id);
	        WorkOrder workOrder = workOrderRepository.findById(id)
	                .orElseThrow(() ->
	                        new ResourceNotFoundException("Work Order not found"));

	        workOrderRepository.delete(workOrder);
	    }

	    // ---------------- Helper Methods ----------------

	    private String generateWorkOrderCode() {

	        long count = workOrderRepository.count() + 1;

	        return String.format("WO-%05d", count);

	    }

	    private void saveStatusHistory(WorkOrder workOrder,
	                                   WorkOrderStatus oldStatus,
	                                   WorkOrderStatus newStatus,
	                                   String remarks,
	                                   User changedBy) {

	        WorkOrderStatusHistory history = WorkOrderStatusHistory.builder()
	                .workOrder(workOrder)
	                .oldStatus(oldStatus)
	                .newStatus(newStatus)
	                .remarks(remarks)
	                .changedBy(changedBy)
	                .build();

	        historyRepository.save(history);
	    }

}
