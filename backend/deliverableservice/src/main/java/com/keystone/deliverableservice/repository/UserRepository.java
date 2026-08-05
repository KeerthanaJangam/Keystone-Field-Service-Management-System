package com.keystone.deliverableservice.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.deliverableservice.entity.User;
import com.keystone.deliverableservice.enums.Role;

import java.util.List;

public interface UserRepository extends JpaRepository<User,Long>
{
	Optional<User> findByEmail(String email);
	
	List<User> findByRole(Role role);
	
	boolean existsByEmail(String email);
}
