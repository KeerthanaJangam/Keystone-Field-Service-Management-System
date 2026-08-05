package com.keystone.deliverableservice.mapper;

import com.keystone.deliverableservice.dto.request.WorkOrderRequest;
import com.keystone.deliverableservice.dto.response.WorkOrderResponse;
import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.entity.Site;
import com.keystone.deliverableservice.entity.WorkOrder;
import com.keystone.deliverableservice.enums.WorkOrderStatus;

public class WorkOrderMapper {
	 private WorkOrderMapper() {
	    }

	    public static WorkOrder toEntity(WorkOrderRequest request,
	                                     Customer customer,
	                                     Site site) {

	        WorkOrder workOrder = new WorkOrder();

	        workOrder.setTitle(request.getTitle());
	        workOrder.setDescription(request.getDescription());
	        workOrder.setPriority(request.getPriority());
	        workOrder.setCustomer(customer);
	        workOrder.setSite(site);

	        // Default status for newly created work orders
	        workOrder.setStatus(WorkOrderStatus.NEW);

	        return workOrder;
	    }

	    public static WorkOrderResponse toResponse(WorkOrder workOrder) {

	        return WorkOrderResponse.builder()
	                .id(workOrder.getId())
	                .workOrderCode(workOrder.getWorkOrderCode())
	                .title(workOrder.getTitle())
	                .description(workOrder.getDescription())
	                .priority(workOrder.getPriority())
	                .status(workOrder.getStatus())
	                .slaDueDate(workOrder.getSlaDueDate())
	                .createdAt(workOrder.getCreatedAt())
	                .customerName(workOrder.getCustomer().getCompanyName())
	                .siteName(workOrder.getSite().getSiteName())
	                .technicianName(
	                        workOrder.getAssignedTechnician() != null
	                                ? workOrder.getAssignedTechnician().getName()
	                                : null
	                )
	                .build();
	    }

	    public static void updateEntity(WorkOrder workOrder,
	                                    WorkOrderRequest request,
	                                    Customer customer,
	                                    Site site) {

	        workOrder.setTitle(request.getTitle());
	        workOrder.setDescription(request.getDescription());
	        workOrder.setPriority(request.getPriority());
	        workOrder.setCustomer(customer);
	        workOrder.setSite(site);
	    }
}
