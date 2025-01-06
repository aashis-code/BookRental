package com.bookrental.exceptions;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.bookrental.helper.ResponseObject;

import ch.qos.logback.core.status.Status;

@RestControllerAdvice
public class GlobalExceptionHandler {

	// Validation Field Exception Handling
	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseObject methodArgumentValidException(MethodArgumentNotValidException ex) {
		Map<String, List<String>> errorHashMap = new HashMap<>();

		ex.getBindingResult().getAllErrors().forEach(error -> {
			String field = ((FieldError) error).getField();
			String defaultMessage = error.getDefaultMessage();

			if (errorHashMap.containsKey(field)) {
				errorHashMap.get(field).add(defaultMessage);
			} else {
				List<String> errorMessages = new ArrayList<>();
				errorMessages.add(defaultMessage);
				errorHashMap.put(field, errorMessages);
			}
		});
		return ResponseObject.builder().status(false).message("Validation error.").data(errorHashMap).build();
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(MethodArgumentTypeMismatchException.class)
	public ResponseObject methodArgumentTypeMismatchException(MethodArgumentTypeMismatchException ex) {
		String message = ex.getValue().toString();
		return ResponseObject.builder().status(false).message(String.format("The provided value '%s' is invalid.", message))
				.data(null).build();
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseObject resourceNotFoundException(ResourceNotFoundException ex) {
		return ResponseObject.builder().status(false).message(ex.getMessage()).data("").build();
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(ResourceAlreadyExist.class)
	public ResponseObject resourceAlreadyExistException(ResourceAlreadyExist ex) {
		return ResponseObject.builder().status(false).message(ex.getMessage()).data("").build();
	}

	@ResponseStatus(value = HttpStatus.BAD_REQUEST)
	@ExceptionHandler(DataIntegrityViolationException.class)
	public ResponseObject dataIntegrityViolationException(DataIntegrityViolationException ex) {
		String message = ex.getMessage();
		int startIndex = message.indexOf("(");
		int lastIndex = message.indexOf(")");
		return ResponseObject.builder().status(false).message(String.format("The provided %s is already taken.", message.substring(startIndex+1, lastIndex))).data("")
				.build();
	}
	
	@ExceptionHandler(FileUploadFailException.class)
	public ResponseObject fileUploadFailException(FileUploadFailException ex) {
		return ResponseObject.builder()
				.status(false)
				.message(ex.getMessage())
				.data(null)
				.build();
	}

	@ResponseStatus(value = HttpStatus.UNAUTHORIZED)
	@ExceptionHandler(UnAuthorizedException.class)
	public ResponseObject unAuthorizedException(UnAuthorizedException ex) {
		return ResponseObject.builder().status(false).message("UnauthorizedException occur !!").data(ex.getMessage())
				.build();
	}
}
