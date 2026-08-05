package com.keystone.deliverableservice.service;

import java.util.List;

import com.keystone.deliverableservice.dto.request.AssignTechnicianRequest;
import com.keystone.deliverableservice.dto.request.WorkOrderRequest;
import com.keystone.deliverableservice.dto.response.WorkOrderResponse;

public interface WorkOrderService 
{
	WorkOrderResponse createWorkOrder(WorkOrderRequest request);
	WorkOrderResponse updateWorkOrder(Long id, WorkOrderRequest request);
	
	WorkOrderResponse getWorkOrder(Long id);
	
	List<WorkOrderResponse> getAllWorkOrders();
	
	WorkOrderResponse assignTechnician(Long workOrderId, AssignTechnicianRequest request);
	
	void deleteWorkOrder(Long id);

}
