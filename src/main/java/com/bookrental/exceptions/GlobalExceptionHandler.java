package com.bookrental.exceptions;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;


@ControllerAdvice
public class GlobalExceptionHandler {

	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Map<String, String>> resourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(Map.of("message", message), HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(ResourceAlreadyExist.class)
	public ResponseEntity<Map<String, String>> resourceAlreadyExistException(ResourceAlreadyExist ex){
		String message = ex.getMessage();
		return new ResponseEntity<>(Map.of("message", message), HttpStatus.NOT_FOUND);
	}
}
