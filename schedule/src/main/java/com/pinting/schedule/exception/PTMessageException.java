package com.pinting.schedule.exception;

import com.pinting.business.enums.PTMessageEnum;
import com.pinting.core.exception.PTException;

/**
 * 消息异常
 * @Project: business
 * @Title: PTMessageException.java
 * @author Zhou Changzai
 * @date 2015-1-23 下午7:29:06
 * @Copyright: 2015 BiGangWan.com Inc. All rights reserved.
 */
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
