package com.bookrental.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.bookrental.helper.ResponseObject;


@ControllerAdvice
public class GlobalExceptionHandler {
	
	//Validation Field Exception Handling
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseObject methodArgumentValidException(MethodArgumentNotValidException ex){
		Map<String, List<String>> errorHashMap = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError)error).getField();
			String defaultMessage = error.getDefaultMessage();
			
			if(errorHashMap.containsKey(field)) {
				errorHashMap.get(field).add(defaultMessage);
			}
			else {
				List<String> errorMessages = new ArrayList<>();
				errorMessages.add(defaultMessage);
				errorHashMap.put(field, errorMessages);
			}
		});
		return new ResponseObject(false,"Validation errors.",errorHashMap);
	}

	
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
	
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseObject unAuthorizedException(UnAuthorizedException ex) {
		String message = ex.getMessage();
		return new ResponseObject(false, "Unauthorization", message);
	}
}
