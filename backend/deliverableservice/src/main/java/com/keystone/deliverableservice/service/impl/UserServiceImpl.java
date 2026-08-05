package com.keystone.deliverableservice.service.impl;


import java.util.List;
import org.springframework.stereotype.Service;

import com.keystone.deliverableservice.dto.request.UserResponse;
import com.keystone.deliverableservice.entity.User;
import com.keystone.deliverableservice.enums.Role;
import com.keystone.deliverableservice.repository.UserRepository;
import com.keystone.deliverableservice.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Override
    public List<UserResponse> getTechnicians() {

        return userRepository.findByRole(Role.TECHNICIAN)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    private UserResponse mapToResponse(User user) {

        return UserResponse.builder()
                .id(user.getId())
                .name(user.getName())
                .email(user.getEmail())
                .build();

    }

}
