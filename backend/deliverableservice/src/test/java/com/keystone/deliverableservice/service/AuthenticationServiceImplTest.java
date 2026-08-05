package com.keystone.deliverableservice.service;

import com.keystone.deliverableservice.dto.request.LoginRequest;
import com.keystone.deliverableservice.dto.request.RegisterRequest;
import com.keystone.deliverableservice.dto.response.LoginResponse;
import com.keystone.deliverableservice.entity.User;
import com.keystone.deliverableservice.enums.Role;
import com.keystone.deliverableservice.security.CustomUserDetails;
import com.keystone.deliverableservice.security.JwtService;
import com.keystone.deliverableservice.service.impl.AuthenticationServiceImpl;
import com.keystone.deliverableservice.repository.UserRepository;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceImplTest 
{
	   @Mock
	    private UserRepository userRepository;

	    @Mock
	    private PasswordEncoder passwordEncoder;

	    @Mock
	    private AuthenticationManager authenticationManager;

	    @Mock
	    private JwtService jwtService;

	    @InjectMocks
	    private AuthenticationServiceImpl authenticationService;
	    
	    @Test
	    void register_ShouldRegisterUserSuccessfully() {

	        RegisterRequest request = new RegisterRequest();
	        request.setName("John");
	        request.setEmail("john@test.com");
	        request.setPassword("password123");
	        request.setRole(Role.DISPATCHER);

	        when(userRepository.existsByEmail("john@test.com"))
	                .thenReturn(false);

	        when(passwordEncoder.encode("password123"))
	                .thenReturn("encodedPassword");

	        when(userRepository.save(any(User.class)))
	                .thenAnswer(invocation -> invocation.getArgument(0));

	        authenticationService.register(request);

	        verify(userRepository)
	                .existsByEmail("john@test.com");

	        verify(passwordEncoder)
	                .encode("password123");

	        verify(userRepository)
	                .save(any(User.class));
	    }
	    @Test
	    void register_ShouldThrowException_WhenEmailAlreadyExists() {

	        RegisterRequest request = new RegisterRequest();
	        request.setEmail("john@test.com");

	        when(userRepository.existsByEmail("john@test.com"))
	                .thenReturn(true);

	        IllegalArgumentException exception =
	                assertThrows(
	                        IllegalArgumentException.class,
	                        () -> authenticationService.register(request)
	                );

	        assertEquals(
	                "Email already registered.",
	                exception.getMessage()
	        );

	        verify(userRepository)
	                .existsByEmail("john@test.com");

	        verify(userRepository, never())
	                .save(any(User.class));

	        verify(passwordEncoder, never())
	                .encode(anyString());
	    }
	    @Test
	    void login_ShouldReturnJwtToken() {

	        LoginRequest request = new LoginRequest();
	        request.setEmail("john@test.com");
	        request.setPassword("password123");

	        User user = User.builder()
	                .id(1L)
	                .name("John")
	                .email("john@test.com")
	                .password("encodedPassword")
	                .role(Role.DISPATCHER)
	                .build();

	        when(userRepository.findByEmail("john@test.com"))
	                .thenReturn(Optional.of(user));

	        when(jwtService.generateToken(any(CustomUserDetails.class)))
	                .thenReturn("jwt-token");

	        LoginResponse response =
	                authenticationService.login(request);

	        assertNotNull(response);

	        assertEquals("jwt-token", response.getToken());

	        assertEquals("john@test.com", response.getEmail());

	        assertEquals(Role.DISPATCHER, response.getRole());

	        verify(authenticationManager)
	                .authenticate(any(UsernamePasswordAuthenticationToken.class));

	        verify(jwtService)
	                .generateToken(any(CustomUserDetails.class));
	    }
	    @Test
	    void login_ShouldThrowException_WhenCredentialsAreInvalid() {

	        LoginRequest request = new LoginRequest();
	        request.setEmail("john@test.com");
	        request.setPassword("wrongPassword");

	        doThrow(new BadCredentialsException("Bad credentials"))
	                .when(authenticationManager)
	                .authenticate(any(UsernamePasswordAuthenticationToken.class));

	        assertThrows(
	                BadCredentialsException.class,
	                () -> authenticationService.login(request)
	        );

	        verify(authenticationManager)
	                .authenticate(any(UsernamePasswordAuthenticationToken.class));

	        verify(jwtService, never())
	                .generateToken(any());
	    }
}
