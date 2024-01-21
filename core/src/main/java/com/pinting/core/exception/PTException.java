package com.pinting.core.exception;

import com.pinting.core.util.StringUtil;

public class PTException extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4806943448116961517L;
	
	/**
     * 异常代码
     */
	private String errCode = "";

    /**
     * 异常信息
     */
	private String errMessage = "";

	/**
	 * 
	 * @param message
	 */
	public PTException(String message) {
		super(message);
		this.errMessage = message;
	}

    /**
     * 
     * @param code
     * @param message
     */
    public PTException(String code, String message) {
        super(message);
        errCode = code.trim();
        this.errMessage = message.trim();
    }
    /**
     * 
     * @param cause
     */
    public PTException(Throwable cause) {
        super(cause);
    }
    /**
     * 
     * @param errCode
     * @param errMessage
     * @param cause
     */
    public PTException(String errCode, String errMessage, Throwable cause) {
		super(cause);
		this.errCode = errCode.trim();
		this.errMessage = errMessage.trim();
	}
    
    /**
     * 
     * @param code
     * @param sMessage
     * @param errMessage
     */
    public PTException(String code, String sMessage, String errMessage) {
        super(sMessage.trim());
        this.errCode = code.trim();
        this.errMessage= errMessage.trim();
    }
    
    
    public String getErrCode() {
        return this.errCode;
    }
    
	public String getErrMessage() {
		String errMsg = StringUtil.isBlank(this.errMessage) ? super.getMessage() : this.errMessage;
		return errMsg;
	}

}
