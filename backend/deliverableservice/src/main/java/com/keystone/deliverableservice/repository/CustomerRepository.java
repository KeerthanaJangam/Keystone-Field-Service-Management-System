package com.keystone.deliverableservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.deliverableservice.entity.Customer;

public interface CustomerRepository  extends JpaRepository<Customer, Long>
{
	Optional<Customer> findByCompanyName(String companyName);

    boolean existsByCompanyName(String companyName);
}
