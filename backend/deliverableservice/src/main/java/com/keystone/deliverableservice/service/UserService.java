package com.keystone.deliverableservice.service;
import java.util.List;

import com.keystone.deliverableservice.dto.request.UserResponse;

public interface UserService {
	   List<UserResponse> getTechnicians();
}
