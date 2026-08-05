package com.keystone.deliverableservice.entity;

import java.time.LocalDateTime;
import java.util.List;

import com.keystone.deliverableservice.enums.Role;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User 
{
 @Id
 @GeneratedValue(strategy = GenerationType.IDENTITY)
 private Long id;
 
 @NotBlank(message = "Name is required")
 @Column(nullable = false)
 private String name;
 
 @Email
 @Column(nullable = false, unique = true)
 private String email;
 
 @Column(nullable = false)
 private String password;
 
 @Enumerated(EnumType.STRING)
 @Column(nullable = false)
 private Role role;
 
 @Column(nullable = false)
 private LocalDateTime createdAt;
 
 @OneToMany(mappedBy = "assignedTechnician")
 private List<WorkOrder> assignedWorkOrders;
 
 @PrePersist
 public void prePersist() 
 {
	createdAt = LocalDateTime.now(); 
 }
}
