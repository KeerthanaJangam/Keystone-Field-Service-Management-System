package com.keystone.deliverableservice.service;

import java.util.List;

import com.keystone.deliverableservice.dto.request.SiteRequest;
import com.keystone.deliverableservice.dto.response.SiteResponse;

public interface SiteService 
{
	SiteResponse createSite(SiteRequest request);
	SiteResponse updateSite(Long id, SiteRequest request);
	SiteResponse getSiteById(Long id);
	List<SiteResponse> getAllSites();
	List<SiteResponse> getSitesByCustomer(Long customerId);
	void deleteSite(Long id);
 
}
