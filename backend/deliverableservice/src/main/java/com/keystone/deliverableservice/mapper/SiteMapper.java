package com.keystone.deliverableservice.mapper;

import com.keystone.deliverableservice.dto.request.SiteRequest;
import com.keystone.deliverableservice.dto.response.SiteResponse;
import com.keystone.deliverableservice.entity.Customer;
import com.keystone.deliverableservice.entity.Site;

public class SiteMapper {
	private SiteMapper() {
    }

    public static Site toEntity(SiteRequest request,
                                Customer customer) {

        Site site = new Site();

        site.setSiteName(request.getSiteName());
        site.setAddress(request.getAddress());
        site.setCity(request.getCity());
        site.setState(request.getState());
        site.setPincode(request.getPincode());
        site.setCustomer(customer);

        return site;
    }

    public static SiteResponse toResponse(Site site) {

        return SiteResponse.builder()
                .id(site.getId())
                .siteName(site.getSiteName())
                .address(site.getAddress())
                .city(site.getCity())
                .state(site.getState())
                .pincode(site.getPincode())
                .customerId(site.getCustomer().getId())
                .customerName(site.getCustomer().getCompanyName())
                .build();
    }

    public static void updateEntity(Site site,
                                    SiteRequest request,
                                    Customer customer) {

        site.setSiteName(request.getSiteName());
        site.setAddress(request.getAddress());
        site.setCity(request.getCity());
        site.setState(request.getState());
        site.setPincode(request.getPincode());
        site.setCustomer(customer);
    }

}
