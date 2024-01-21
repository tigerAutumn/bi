package com.pinting.open.base.response;

import com.pinting.open.base.exception.OpenException;

public class Response {

	private OpenException exception;

	private boolean success;

	public OpenException getException() {
		return exception;
	}

	public void setException(OpenException exception) {
		this.exception = exception;
	}

	public boolean isSuccess() {
		return success;
	}

	public void setSuccess(boolean success) {
		this.success = success;
	}

}
