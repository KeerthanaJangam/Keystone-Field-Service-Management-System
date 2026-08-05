package com.keystone.deliverableservice.dto.request;

import com.keystone.deliverableservice.enums.Priority;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkOrderRequest 
{
@NotBlank
private String title;
private String description;
@NotNull
private Priority priority;
 @NotNull
 private Long customerId;
 @NotNull
 private Long siteId;
}
