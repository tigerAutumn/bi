package com.pinting.open.base.exception;

public class OpenException extends RuntimeException {

	private String code;

	private String message;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
	
	public OpenException() {
		
	}
	
	public OpenException(String code, String message) {
		this.code = code;
		this.message = message;
	}

}
