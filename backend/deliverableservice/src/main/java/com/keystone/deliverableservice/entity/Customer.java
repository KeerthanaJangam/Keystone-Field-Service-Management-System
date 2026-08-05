package com.keystone.deliverableservice.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import java.util.List;


@Entity
@Table(name="customers")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Customer 
{
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  
  @NotBlank
  @Column(nullable = false)
  private String companyName;
  
  @NotBlank
  @Column(nullable = false)
  private String contactPerson;
  
  @Email
  @Column(nullable = false)
  private String email;
  
  @Column(nullable = false)
  private String phone;
  
  @Column(nullable = false)
  private String address;
  
  @OneToMany(mappedBy = "customer",
		  	cascade = CascadeType.ALL,
		  	orphanRemoval = true)
  private List<Site> sites;
  
  @OneToMany(mappedBy = "customer")
  private List<WorkOrder> workOrders;
  
}
