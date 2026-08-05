package com.keystone.deliverableservice.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.keystone.deliverableservice.dto.request.SiteRequest;
import com.keystone.deliverableservice.dto.response.SiteResponse;
import com.keystone.deliverableservice.service.SiteService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/sites")
@RequiredArgsConstructor
public class SiteController
{
	@Autowired
	private SiteService siteService;
	
	@PostMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
	public ResponseEntity<SiteResponse> createSite( @Valid @RequestBody SiteRequest request){
		
		 return new ResponseEntity<>(
				 siteService.createSite(request),
				 HttpStatus.CREATED);	
	}
	
	@GetMapping
	@PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<SiteResponse>> getAllSites() {

        return ResponseEntity.ok(siteService.getAllSites());
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<SiteResponse> getSite(
            @PathVariable Long id) {

        return ResponseEntity.ok(siteService.getSiteById(id));
    }
    @GetMapping("/customer/{customerId}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<List<SiteResponse>> getSitesByCustomer(
            @PathVariable Long customerId) {

        return ResponseEntity.ok(
                siteService.getSitesByCustomer(customerId)
        );
    }
    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'DISPATCHER')")
    public ResponseEntity<SiteResponse> updateSite(
            @PathVariable Long id,
            @Valid @RequestBody SiteRequest request) {

        return ResponseEntity.ok(
                siteService.updateSite(id, request)
        );
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN')")
    public ResponseEntity<Void> deleteSite(
            @PathVariable Long id) {

        siteService.deleteSite(id);

        return ResponseEntity.noContent().build();
    }
}
