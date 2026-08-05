package com.keystone.deliverableservice.service.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.keystone.deliverableservice.dto.request.SiteRequest;
import com.keystone.deliverableservice.dto.response.SiteResponse;
import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.entity.Site;
import com.keystone.deliverableservice.exception.ResourceNotFoundException;
import com.keystone.deliverableservice.mapper.SiteMapper;
import com.keystone.deliverableservice.repository.CustomerRepository;
import com.keystone.deliverableservice.repository.SiteRepository;
import com.keystone.deliverableservice.service.SiteService;

import lombok.RequiredArgsConstructor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
@RequiredArgsConstructor
@Transactional
public class SiteServiceImpl implements SiteService {
	private final SiteRepository siteRepository;
    private final CustomerRepository customerRepository;

    private static final Logger logger =
            LoggerFactory.getLogger(SiteServiceImpl.class);
    
    @Override
    public SiteResponse createSite(SiteRequest request) {

    	logger.info("Creating site for customer ID: {}", request.getCustomerId());
    	
        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + request.getCustomerId()));

        Site site = SiteMapper.toEntity(request, customer);

        Site savedSite = siteRepository.save(site);
        
        logger.info("Site created successfully. Site ID: {}", site.getId());

        return SiteMapper.toResponse(savedSite);
    }

    @Override
    public SiteResponse updateSite(Long id, SiteRequest request) {

    	logger.info("Updating site with ID: {}", id);
    	
        Site site = siteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Site not found with id: " + id));

        Customer customer = customerRepository.findById(request.getCustomerId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + request.getCustomerId()));

        SiteMapper.updateEntity(site, request, customer);

        Site updatedSite = siteRepository.save(site);

        return SiteMapper.toResponse(updatedSite);
    }

    @Override
    @Transactional(readOnly = true)
    public SiteResponse getSiteById(Long id) {

    	logger.debug("Fetching site with ID: {}", id);
    	
        Site site = siteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Site not found with id: " + id));

        return SiteMapper.toResponse(site);
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteResponse> getAllSites() {

        return siteRepository.findAll()
                .stream()
                .map(SiteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<SiteResponse> getSitesByCustomer(Long customerId) {

        Customer customer = customerRepository.findById(customerId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Customer not found with id: " + customerId));

        return siteRepository.findByCustomer(customer)
                .stream()
                .map(SiteMapper::toResponse)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteSite(Long id) {
    	logger.info("Deleting site with ID: {}", id);
    	
        Site site = siteRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Site not found with id: " + id));

        siteRepository.delete(site);
 }
}