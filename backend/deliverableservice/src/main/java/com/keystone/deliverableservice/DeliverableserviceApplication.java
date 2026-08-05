package com.keystone.deliverableservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = "com.keystone.deliverableservice" )
public class DeliverableserviceApplication {

	public static void main(String[] args) {
		SpringApplication.run(DeliverableserviceApplication.class, args);
		System.out.println("Project is Running..... ");
	}

}
