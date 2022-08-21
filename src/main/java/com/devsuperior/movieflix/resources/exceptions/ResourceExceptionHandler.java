package com.devsuperior.movieflix.resources.exceptions;

import java.time.Instant;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.devsuperior.movieflix.services.exceptions.ResourceNotFoundException;

@ControllerAdvice
public class ResourceExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<StandardError> entityNotFound(ResourceNotFoundException e, HttpServletRequest req) {
		StandardError err = new StandardError();
		HttpStatus status = HttpStatus.NOT_FOUND;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Resource not found");
		err.setMessage(e.getMessage());
		err.setPath(req.getRequestURI());
		return ResponseEntity.status(status).body(err);
	}
	
	
	@ExceptionHandler(MethodArgumentNotValidException.class) 
	public ResponseEntity<ValidationError> fieldsValidation(MethodArgumentNotValidException e, HttpServletRequest req) {
		ValidationError err = new ValidationError();
		HttpStatus status = HttpStatus.UNPROCESSABLE_ENTITY;
		err.setTimestamp(Instant.now());
		err.setStatus(status.value());
		err.setError("Field validation exception");
		err.setMessage(e.getMessage());
		err.setPath(req.getRequestURI());
		
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {
			err.addError(fieldError.getField(), fieldError.getDefaultMessage());
		}
		
		return ResponseEntity.status(status).body(err);
	}
}
