package com.keystone.deliverableservice.exception;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.keystone.deliverableservice.dto.response.ErrorResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
@RestControllerAdvice
public class GlobalExceptionHandler 
{
	private static final Logger logger =
	        LoggerFactory.getLogger(GlobalExceptionHandler.class);
	
	 @ExceptionHandler(ResourceNotFoundException.class)
	    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResourceNotFoundException ex) {
		 logger.warn("Resource not found: {}", ex.getMessage());
		 
	        ErrorResponse error = ErrorResponse.builder()
	                .timestamp(LocalDateTime.now())
	                .status(HttpStatus.NOT_FOUND.value())
	                .error("Not Found")
	                .message(ex.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
	    }

	    @ExceptionHandler(IllegalArgumentException.class)
	    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {

	    	 logger.warn("Validation failed: {}", ex.getMessage());
	    	
	        ErrorResponse error = ErrorResponse.builder()
	                .timestamp(LocalDateTime.now())
	                .status(HttpStatus.BAD_REQUEST.value())
	                .error("Bad Request")
	                .message(ex.getMessage())
	                .build();

	        return ResponseEntity.badRequest().body(error);
	    }

	    @ExceptionHandler(MethodArgumentNotValidException.class)
	    public ResponseEntity<ErrorResponse> handleValidation(MethodArgumentNotValidException ex) {
	    	logger.warn("Request validation failed.");
	        String message = ex.getBindingResult()
	                .getFieldErrors()
	                .stream()
	                .map(error -> error.getField() + ": " + error.getDefaultMessage())
	                .collect(Collectors.joining(", "));

	        ErrorResponse error = ErrorResponse.builder()
	                .timestamp(LocalDateTime.now())
	                .status(HttpStatus.BAD_REQUEST.value())
	                .error("Validation Failed")
	                .message(message)
	                .build();

	        return ResponseEntity.badRequest().body(error);
	    }

	    @ExceptionHandler(Exception.class)
	    public ResponseEntity<ErrorResponse> handleGeneric(Exception ex) {
	        logger.error(
	                "Unexpected exception occurred.",
	                ex
	        );
	        
	        ErrorResponse error = ErrorResponse.builder()
	                .timestamp(LocalDateTime.now())
	                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
	                .error("Internal Server Error")
	                .message(ex.getMessage())
	                .build();

	        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
	    }
	

}
