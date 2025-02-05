package com.bookrental.controller;

import com.bookrental.helper.ResponseObject;
import com.bookrental.serviceimpl.CustomMessageSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

public class BaseController {

	private final String SUCCESS = "success !!";
	private final String ERROR = "error !!";

	@Autowired
	public CustomMessageSource customMessageSource;

	public ResponseObject getSuccessResponse(String message, Object data) {
		return ResponseObject.builder()
				.status(true)
				.message(message)
				.data(data)
				.build();
	}

	public ResponseObject getFailureResponse(String message, Object data) {
		return ResponseObject.builder()
				.status(false)
				.message(message)
				.data(data)
				.build();
	}

}
