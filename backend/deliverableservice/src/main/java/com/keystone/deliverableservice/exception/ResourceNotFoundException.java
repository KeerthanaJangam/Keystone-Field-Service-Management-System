package com.keystone.deliverableservice.exception;

public class ResourceNotFoundException  extends RuntimeException
{
   public ResourceNotFoundException(String message) {
	   super(message);
   }
}
