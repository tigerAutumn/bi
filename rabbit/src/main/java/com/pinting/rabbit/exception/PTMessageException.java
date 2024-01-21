package com.pinting.rabbit.exception;

import com.pinting.core.exception.PTException;
import com.pinting.rabbit.enums.PTMessageEnum;


public class PTMessageException extends PTException {
	/**
	 * 构造一个消息异常,用来处理系统中需要给外层返回的消息
	 * @param messageEnum 
	 */
	public PTMessageException(PTMessageEnum messageEnum){
		super(messageEnum.getCode(), messageEnum.getMessage());
	}
	
	/**
	 * 构造一个消息异常,用来处理系统中需要给外层返回的消息, 再加上额外的消息内容
	 * @param messageEnum PTMessageEnum
	 * @param extraMessage 额外的消息内容
	 */
	public PTMessageException(PTMessageEnum messageEnum, String extraMessage){
		super(messageEnum.getCode(), messageEnum.getMessage() + extraMessage);
	}

	/**
	 * 构造一个消息异常,用来处理系统中需要给外层返回的消息
	 * @param code
	 * @param message
	 */
	public PTMessageException(String code, String message){
		super(code, message);
	}
}
