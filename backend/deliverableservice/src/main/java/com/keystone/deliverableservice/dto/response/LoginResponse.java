package com.keystone.deliverableservice.dto.response;

import com.keystone.deliverableservice.enums.Role;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
	private String token;
	private String name;
	private String email;
	private Role role;

}
