package com.keystone.deliverableservice.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class SiteRequest
{
@NotBlank
private String siteName;

@NotBlank
private String address;
private String city;
private String state;
private String pincode;

@NotNull
private Long customerId;

}
