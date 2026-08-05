package com.keystone.deliverableservice.service;

import com.keystone.deliverableservice.dto.request.LoginRequest;
import com.keystone.deliverableservice.dto.request.RegisterRequest;
import com.keystone.deliverableservice.dto.response.LoginResponse;

public interface AuthenticationService {

    void register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}