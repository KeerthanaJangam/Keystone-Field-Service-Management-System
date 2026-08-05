package com.keystone.deliverableservice.controller;

import com.keystone.deliverableservice.dto.request.LoginRequest;
import com.keystone.deliverableservice.dto.request.RegisterRequest;
import com.keystone.deliverableservice.dto.response.ApiResponse;
import com.keystone.deliverableservice.dto.response.LoginResponse;
import com.keystone.deliverableservice.service.AuthenticationService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService authenticationService;

    @PostMapping("/register")
    public ResponseEntity<ApiResponse> register(
            @Valid @RequestBody RegisterRequest request) {

        authenticationService.register(request);

        ApiResponse response = ApiResponse.builder()
                .success(true)
                .message("User registered successfully.")
                .build();

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
            @Valid @RequestBody LoginRequest request) {

        LoginResponse response = authenticationService.login(request);

        return ResponseEntity.ok(response);
    }
}