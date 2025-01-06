package com.bookrental.exceptions;

public class FileUploadFailException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public FileUploadFailException(String message) {
		super(message);
	}
}
