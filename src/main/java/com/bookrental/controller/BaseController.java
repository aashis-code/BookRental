package com.bookrental.controller;

import com.bookrental.helper.ResponseObject;

public class BaseController {

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