package com.keystone.deliverableservice.dto.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class CustomerResponse
{
private Long id;
private String companyName;
private String contactPerson;
private String email;
private String phone;
private String address;
}
