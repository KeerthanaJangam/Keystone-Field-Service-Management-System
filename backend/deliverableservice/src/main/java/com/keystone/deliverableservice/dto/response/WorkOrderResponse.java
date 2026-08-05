package com.keystone.deliverableservice.dto.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.keystone.deliverableservice.enums.Priority;
import com.keystone.deliverableservice.enums.WorkOrderStatus;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class WorkOrderResponse 
{
private Long id;
private String workOrderCode;
private String title;
private String description;
private Priority priority;
private WorkOrderStatus status;
private LocalDate slaDueDate;
private LocalDateTime createdAt;
private String customerName;
private String siteName;
private String technicianName;

}
