package com.keystone.deliverableservice.service.impl;

import com.keystone.deliverableservice.dto.request.LoginRequest;
import com.keystone.deliverableservice.dto.request.RegisterRequest;
import com.keystone.deliverableservice.dto.response.LoginResponse;
import com.keystone.deliverableservice.entity.User;
import com.keystone.deliverableservice.repository.UserRepository;
import com.keystone.deliverableservice.security.CustomUserDetails;
import com.keystone.deliverableservice.security.JwtService;
import com.keystone.deliverableservice.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Transactional
public class AuthenticationServiceImpl implements AuthenticationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    
    private static final Logger logger =
            LoggerFactory.getLogger(AuthenticationServiceImpl.class);

    @Override
    public void register(RegisterRequest request) {

    	logger.info("Registering user with email: {}", request.getEmail());
    	
        if (userRepository.existsByEmail(request.getEmail())) {
        	
        	 // Log before throwing the exception
            logger.warn("Registration failed. Email already exists: {}", request.getEmail());
        	
            throw new IllegalArgumentException("Email already registered.");
        }

        User user = User.builder()
                .name(request.getName())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role(request.getRole())
                .build();

        userRepository.save(user);
        
        // Log after successful registration
        logger.info("User registered successfully: {}", request.getEmail());
    }

    @Override
    public LoginResponse login(LoginRequest request) {
    	
    	logger.info("Login attempt for email: {}", request.getEmail());
       try {
    	   
    	   authenticationManager.authenticate(
    			   new UsernamePasswordAuthenticationToken(
    					   request.getEmail(),
    					   request.getPassword()
    					   )
    			   );
       } catch (Exception ex) {

    	    logger.warn( "Failed login attempt for email: {}", request.getEmail());

    	    throw ex;
    	}

        CustomUserDetails userDetails =
                (CustomUserDetails) userRepository.findByEmail(request.getEmail())
                        .map(CustomUserDetails::new)
                        .orElseThrow(() -> new IllegalArgumentException("Invalid email or password."));

        String token = jwtService.generateToken(userDetails);

        logger.info("User logged in successfully: {}", request.getEmail());
        
        return LoginResponse.builder()
                .token(token)
                .name(userDetails.getName())
                .email(userDetails.getUsername())
                .role(userDetails.getUser().getRole())
                .build();
    }
}