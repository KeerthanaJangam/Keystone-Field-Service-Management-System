package com.keystone.deliverableservice.controller;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import com.keystone.deliverableservice.dto.request.UserResponse;
import com.keystone.deliverableservice.service.UserService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/technicians")
    @PreAuthorize("hasAnyRole('ADMIN','DISPATCHER')")
    public ResponseEntity<List<UserResponse>> getTechnicians() {

        return ResponseEntity.ok(
                userService.getTechnicians()
        );

    }

}
