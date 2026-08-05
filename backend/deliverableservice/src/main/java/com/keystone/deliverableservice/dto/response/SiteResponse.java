package com.keystone.deliverableservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SiteResponse 
{
private Long id;
private String siteName;
private String address;
private String city;
private String state;
private String pincode;
private Long customerId;
private String customerName;
}
