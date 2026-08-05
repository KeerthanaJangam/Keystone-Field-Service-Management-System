package com.keystone.deliverableservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.keystone.deliverableservice.dto.request.AssignTechnicianRequest;
import com.keystone.deliverableservice.dto.request.WorkOrderRequest;
import com.keystone.deliverableservice.dto.response.WorkOrderResponse;
import com.keystone.deliverableservice.service.WorkOrderService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/workorders")
@RequiredArgsConstructor
public class WorkOrderController 
{
	@Autowired
	private WorkOrderService workOrderService;
	
	 @PostMapping
	 @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
	 public ResponseEntity<WorkOrderResponse> createWorkOrder(@Valid @RequestBody WorkOrderRequest request) {

	        return new ResponseEntity<>(
	                workOrderService.createWorkOrder(request),
	                HttpStatus.CREATED
	        );
	    }

	    @GetMapping
	    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER','TECHNICIAN')")
	    public ResponseEntity<List<WorkOrderResponse>> getAllWorkOrders() {

	        return ResponseEntity.ok(
	                workOrderService.getAllWorkOrders()
	        );
	    }

	    @GetMapping("/{id}")
	    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER','TECHNICIAN')")
	    public ResponseEntity<WorkOrderResponse> getWorkOrder(
	            @PathVariable Long id) {

	        return ResponseEntity.ok(
	                workOrderService.getWorkOrder(id)
	        );
	    }

	    @PutMapping("/{id}")
	    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
	    public ResponseEntity<WorkOrderResponse> updateWorkOrder(
	            @PathVariable Long id,
	            @Valid @RequestBody WorkOrderRequest request) {

	        return ResponseEntity.ok(
	                workOrderService.updateWorkOrder(id,request)
	        );
	    }

	    @PutMapping("/{id}/assign")
	    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
	    public ResponseEntity<WorkOrderResponse> assignTechnician(
	            @PathVariable Long id,
	            @Valid @RequestBody AssignTechnicianRequest request) {

	        return ResponseEntity.ok(
	                workOrderService.assignTechnician(id, request)
	        );
	    }

	    @DeleteMapping("/{id}")
	    @PreAuthorize("hasAnyRole('ADMIN')")
	    public ResponseEntity<Void> deleteWorkOrder(
	            @PathVariable Long id) {

	        workOrderService.deleteWorkOrder(id);

	        return ResponseEntity.noContent().build();
	    }

}
