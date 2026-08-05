package com.keystone.deliverableservice.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.entity.Site;

public interface SiteRepository extends JpaRepository<Site, Long>
{
	List<Site> findByCustomer(Customer customer);
}
